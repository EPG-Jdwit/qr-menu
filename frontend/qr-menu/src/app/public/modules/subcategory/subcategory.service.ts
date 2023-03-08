import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { Subcategory, SubcategoryList } from '../../../models/subcategory.model';

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService {

  private subcategoriesUrlPrefix: string;
  private subcategoriesUrlpEnd: string;

  constructor(private http: HttpClient) {
    this.subcategoriesUrlPrefix = 'http://localhost:8081/categories/';
    this.subcategoriesUrlpEnd = '/subcategories';
   }

  getSubcategories(categoryId: number): Observable<SubcategoryList<Subcategory>> {
    return this.http.get<SubcategoryList<Subcategory>>(
      this.subcategoriesUrlPrefix + categoryId + this.subcategoriesUrlpEnd
      );
  }

}
