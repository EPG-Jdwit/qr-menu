import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Category, CategoryList } from 'src/app/models/category.model';
import { AbstractDashboardService } from '../shared/abstract-dashboard.service';

@Injectable({
    providedIn: 'root'
})
export class CategoryDashboardService extends AbstractDashboardService {

    constructor(private http: HttpClient) {
        super();
        this.setUrl('/categories');
    }

    getAll(): Observable<CategoryList<Category>> {
        return this.http.get<CategoryList<Category>>(this.baseUrl);
    }

    deleteById(id : number) : void {
        this.http.delete(this.baseUrl + "/" + id).subscribe(() =>
        // TODO: remove this
          console.log("test")
        );
      }
    
      editById(id: number, product: Category) : void {
        this.http.patch(this.baseUrl + "/" + id, product).subscribe(() =>
        // TODO: remove this
          console.log("test")
        );
      }
    
      create(product: Category) : Observable<Category> {
        return this.http.post<Category>(this.baseUrl, product);
      }
}
