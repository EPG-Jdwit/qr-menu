import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Product, ProductList } from 'src/app/public/modules/product/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductTableService {

  private productUrl: string;

  constructor(private http: HttpClient) { 
    this.productUrl = 'http://localhost:8081/products'
  }

  getProducts(): Observable<ProductList<Product>> {
    return this.http.get<ProductList<Product>>(this.productUrl);
  }
}
