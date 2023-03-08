import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

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

  deleteProduct(id : number) : void {
    this.http.delete(this.productUrl + "/" + id).subscribe(() =>
    // TODO: remove this
      console.log("test")
    );
  }

  updateProduct(id: number, product: Product) : void {
    this.http.patch(this.productUrl + "/" + id, product).subscribe(() =>
    // TODO: remove this
      console.log("test")
    );
  }

  saveProduct(product: Product) : Observable<Product> {
    return this.http.post<Product>(this.productUrl, product);
  }
}
