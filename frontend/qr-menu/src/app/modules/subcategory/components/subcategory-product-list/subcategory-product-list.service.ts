import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { Product, SubcategoryProductList } from 'src/app/modules/product/product.model';

@Injectable({
  providedIn: 'root'
})
export class SubcategoryProductListService {

  private subProductsUrlPrefix: string;
  private subProductsUrlMid: string;
  private subProductsUrlEnd: string;

  constructor(private http: HttpClient ) {
    this.subProductsUrlPrefix = 'http://localhost:8081/categories/';
    this.subProductsUrlMid = '/subcategories/';
    this.subProductsUrlEnd = '/products';
   }

   getSubcategoryProducts(categoryId: number, subcategoryId: number): Observable<SubcategoryProductList<Product>> {
    return this.http.get<SubcategoryProductList<Product>>(
      this.subProductsUrlPrefix + categoryId + this.subProductsUrlMid + subcategoryId + this.subProductsUrlEnd
    )
   }
}
