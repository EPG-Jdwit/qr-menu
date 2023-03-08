import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category, CategoryList } from 'src/app/models/category.model';
import { AbstractDashboardService } from '../common/abstract-dashboard.service';

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
}
