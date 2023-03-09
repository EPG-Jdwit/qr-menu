import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Product, ProductList } from 'src/app/models/product.model';
import { AbstractDashboardService } from '../shared/abstract-dashboard.service';

@Injectable({
  providedIn: 'root'
})
export class ProductDashboardService extends AbstractDashboardService {

  constructor(private http: HttpClient) { 
    super();
    this.setUrl('/products');
  }

  getAll(): Observable<ProductList<Product>> {
    return this.http.get<ProductList<Product>>(this.baseUrl);
  }

  deleteById(id : number) : void {
    this.http.delete(this.baseUrl + "/" + id).subscribe(() =>
    // TODO: remove this
      console.log("test")
    );
  }

  editById(id: number, product: Product) : void {
    this.http.patch(this.baseUrl + "/" + id, product).subscribe(() =>
    // TODO: remove this
      console.log("test")
    );
  }

  create(product: Product) : Observable<Product> {
    return this.http.post<Product>(this.baseUrl, product);
  }
}
