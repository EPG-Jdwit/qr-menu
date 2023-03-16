import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map, mergeMap, switchMap } from 'rxjs/operators';

import { Category, CategoryList } from 'src/app/models/category.model';
import { Product, ProductList } from 'src/app/models/product.model';
import { Subcategory, SubcategoryList } from 'src/app/models/subcategory.model';
import { AbstractDashboardService } from '../shared/abstract-dashboard.service';

@Injectable({
    providedIn: 'root'
})
export class SubcategoryDashboardService extends AbstractDashboardService {
    private alternativeUrl: string = "http://localhost:8081/subcategories"

    constructor(private http: HttpClient) { 
        super();
        this.setUrl('/categories');
    }
    getAll(): Observable<SubcategoryList<Subcategory>> {
        // Get the subcategories
        return this.http.get<SubcategoryList<Subcategory>>(this.alternativeUrl).pipe(
            mergeMap(response => {
                let subcatList = response._embedded.subcategoryList;
                subcatList.map(subcategory => {
                    return this.http.get<Category>(this.baseUrl + "/" + subcategory.categoryId).subscribe( category => {
                        subcategory.category = category;
                        this.http.get<ProductList<Product>>(subcategory._links.products.href).subscribe( productList => {
                            subcategory.products = productList._embedded.productList;
                        })
                    })
                })
                return of(response);
            })
        );
    }

    getCategories(): Observable<CategoryList<Category>> {
        return this.http.get<CategoryList<Category>>(this.baseUrl);
    }

    deleteEntity(subcategory: Subcategory) : void {
        this.http.delete(this.baseUrl + "/" + subcategory.category.id + "/subcategories/" + subcategory.id).subscribe( _ =>
            console.log("deleted")
        );
    }

    editEntity(subcategory: Subcategory) : void {
        this.http.patch(this.baseUrl + "/" + subcategory.category.id + "/subcategories/" + subcategory.id, subcategory).subscribe(() =>
        // TODO: remove this
          console.log("test")
        );
    }

    createEntity(subcategory: Subcategory) : Observable<any> {
        return this.http.post(this.baseUrl + "/" + subcategory.category.id + "/subcategories", subcategory);
    }

    getAllProducts(): Observable<ProductList<Product>> {
        // TODO
        return this.http.get<ProductList<Product>>("http://localhost:8081/products");
    }
    
}
