import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { mergeMap } from 'rxjs/operators';
import { of, from } from 'rxjs';

import { Suggestion, SuggestionList } from '../../suggestion-model';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.scss']
})
export class HomeViewComponent {
  homeText: string;
  private homeTextUrl: string = "assets/home.txt";
  private suggestionsUrl: string = "assets/suggestions.json";
  suggestions: Suggestion[] = [];

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'text/xml'})
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.readTextFile();
    this.readSuggestions();
  }

  // TODO: Move this to service

  private readTextFile(): void {
    this.http.get(this.homeTextUrl, {responseType: 'text'}).subscribe(data => {
      this.homeText = data;
    })
  }

  private readSuggestions(): void {
    const resultList: Suggestion[] = [];

    this.http.get(this.suggestionsUrl).pipe(this.unwrapSuggestions()).subscribe(suggestion => {
      resultList.push(suggestion);
    });
    this.suggestions = resultList;
  }

  private unwrapSuggestions() {
    return mergeMap((response: SuggestionList<Suggestion>) => {
      if (!response || !response.suggestions) {
        return []; // TODO error
      }
      return from(response.suggestions).pipe(
        mergeMap(suggestion => {
            return of({
                url: suggestion.url,
                alt: suggestion.alt,
                description: suggestion.description
            } as Suggestion);
        })
      )
    });
  }
}
