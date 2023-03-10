import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, from } from 'rxjs';
import { mergeMap, map, tap } from 'rxjs/operators';

import { Category, CategoryList } from 'src/app/models/category.model';
import { Subcategory, SubcategoryList } from 'src/app/models/subcategory.model';
import { AbstractDashboardService } from '../shared/abstract-dashboard.service';

@Injectable({
    providedIn: 'root'
})
export class SubcategoryDashboardService extends AbstractDashboardService {

    constructor(private http: HttpClient) { 
        super();
        this.setUrl('/categories');
    }

    getAll(): Observable<SubcategoryList<Subcategory>> {

        var subcategories: Subcategory[] = [];

        var result: SubcategoryList<Subcategory> = {
            _embedded: { subcategoryList: subcategories},
            _links: { self: { href: ''} }
        }
        // array pushen
        // SubcategoryList._embedded.subcategoryList ??

        // Get the categories
        return this.http.get<CategoryList<Category>>(this.baseUrl).pipe(
            // Transform the received response
            mergeMap(response => {
                var subcategories: Subcategory[] = [];

                var result: SubcategoryList<Subcategory> = {
                    _embedded: { subcategoryList: subcategories},
                    _links: { self: { href: ''} }
                }
        
                // Unwrap the category list
                var catList = response._embedded.categoryList;
                for (let i = 0; i < catList.length; i++ ) {
                    // Retrieve the subcategories by using the link
                    this.http.get<SubcategoryList<Subcategory>>(catList[i]._links.subcategories.href).subscribe(subcategoryResponse => {
                        // Check if the category has any subcategories
                        if (subcategoryResponse._embedded) {
                            // Loop over the subcategories and add them to our output array
                            var subcategoryList = subcategoryResponse._embedded.subcategoryList;
                            for (let subcategory of subcategoryList) {
                                subcategory.category = catList[i];
                                subcategories.push(subcategory);
                            }
                        }
                    })
                }
                // Assign the found subcategories to our result object
                // result._embedded.subcategoryList = subcategories;
                return of(result);
            })
        );
    }

    deleteEntity(subcategory: Subcategory) : void {
        
    }

    editEntity(subcategory: Subcategory) : void {
        
    }

    createEntity(subcategory: Subcategory) : Observable<any> {
        return null;
    }
    
}
