import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { AbstractDashboardService } from '../shared/abstract-dashboard.service';
import { Observable, of } from 'rxjs';
import { Entity } from 'src/app/models/entity.model';
import { Suggestion, SuggestionList } from 'src/app/models/suggestion.model';

@Injectable({
    providedIn: 'root'
})
export class SuggestionDashboardService extends AbstractDashboardService {

    constructor(private http: HttpClient) { 
        super();
        this.setUrl('/suggestions');
    }

    createEntity(suggestion: Suggestion): Observable<Suggestion> {
        return this.http.post<Suggestion>(this.baseUrl, suggestion);
    }

    deleteEntity(suggestion: Suggestion): void {
        this.http.delete(this.baseUrl + "/" + suggestion.id).subscribe(() =>
            { error: e => console.error(e); }
        );
    }

    editEntity(suggestion: Suggestion): void {
        this.http.patch(this.baseUrl + "/" + suggestion.id, suggestion).subscribe(() =>
            { error: e => console.error(e); }
        );
    }

    getAll(): Observable<SuggestionList<Suggestion>> {
        return this.http.get<SuggestionList<Suggestion>>(this.baseUrl);
    }
}
