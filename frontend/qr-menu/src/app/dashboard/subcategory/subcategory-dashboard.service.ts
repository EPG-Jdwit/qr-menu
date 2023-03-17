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
                    if (response._embedded) {
                    let subcatList = response._embedded.subcategoryList;
                    subcatList.map(subcategory => {
                        return this.http.get<Category>(this.baseUrl + "/" + subcategory.categoryId).subscribe( category => {
                            subcategory.category = category;
                            this.http.get<ProductList<Product>>(subcategory._links.products.href).subscribe( productList => {
                                if (productList._embedded) {
                                    subcategory.productList = productList._embedded.productList;
                                }
                            })
                        })
                    })
                }
                return of(response);
            })
        );
    }

    getCategories(): Observable<CategoryList<Category>> {
        return this.http.get<CategoryList<Category>>(this.baseUrl);
    }

    deleteEntity(subcategory: Subcategory) : void {
        this.http.delete(this.baseUrl + "/" + subcategory.categoryId + "/subcategories/" + subcategory.id).subscribe( _ =>
            { error: e => console.error(e); }
        );
    }

    editEntity(subcategory: Subcategory) : void {
        this.http.patch(this.baseUrl + "/" + subcategory.category.id + "/subcategories/" + subcategory.id, subcategory).subscribe(() =>
            { error: e => console.error(e); }
        );
    }

    createEntity(subcategory: Subcategory) : Observable<Subcategory> {
        return this.http.post(this.baseUrl + "/" + subcategory.category.id + "/subcategories", subcategory)
            .pipe(map((response: Subcategory) => {
                // Add the category to the subcategory as the backend only provides the categoryId
                response.category = subcategory.category;
                // Add the products to the subcategory as the backend only provides an array of productId's
                response.productList = subcategory.productList;
                return response;
            })
        );
    }

    getAllProducts(): Observable<ProductList<Product>> {
        // TODO : hardcoded url
        return this.http.get<ProductList<Product>>("http://localhost:8081/products");
    }
    
}
