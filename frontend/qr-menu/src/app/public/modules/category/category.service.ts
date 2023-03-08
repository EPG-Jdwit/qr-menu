import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Category, CategoryList } from '../../../models/category.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private categoriesUrl : string;
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json'})
  };


  constructor(private http: HttpClient) { 
    this.categoriesUrl = 'http://localhost:8081/categories';
  }

  getCategories(): Observable<CategoryList<Category>> {
    return this.http.get<CategoryList<Category>>(this.categoriesUrl);
  }

  getCategoryById(id : number): Observable<Category> {
    const url : string = `${this.categoriesUrl}/${id}`;

    return this.http.get<Category>(url);
  }
}
