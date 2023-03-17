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

  deleteEntity(product : Product) : void {
    this.http.delete(this.baseUrl + "/" + product.id).subscribe(() =>
      { error: e => console.error(e); }
    );
  }

  editEntity(product: Product) : void {
    this.http.patch(this.baseUrl + "/" + product.id, product).subscribe(() =>
      { error: e => console.error(e); }
    );
  }

  createEntity(product: Product) : Observable<Product> {
    return this.http.post<Product>(this.baseUrl, product);
  }
}
