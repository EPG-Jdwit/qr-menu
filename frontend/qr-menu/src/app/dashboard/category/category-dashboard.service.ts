import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Category, CategoryList } from 'src/app/models/category.model';
import { Subcategory, SubcategoryList } from 'src/app/models/subcategory.model';
import { AbstractDashboardService } from '../shared/abstract-dashboard.service';

@Injectable({
    providedIn: 'root'
})
export class CategoryDashboardService extends AbstractDashboardService {

    constructor(private http: HttpClient) {
        super();
        this.setUrl('/categories');
    }

    // Get all existing categories while adding their subcategories
    getAll(): Observable<CategoryList<Category>> {
        // Get the categories
        return this.http.get<CategoryList<Category>>(this.baseUrl).pipe(
            // Transform the received response
            mergeMap(response => {
                // Unwrap the category list
                var catList = response._embedded.categoryList;
                for (let i = 0; i < catList.length; i++ ) {
                    // Retrieve the subcategories by using the link
                    this.http.get<SubcategoryList<Subcategory>>(catList[i]._links.subcategories.href).subscribe(subcategoryResponse => {
                        // Check if the category has any subcategories
                        if (subcategoryResponse._embedded) {
                            // Add the subcategory array to the category
                            catList[i].subcategories = subcategoryResponse._embedded.subcategoryList;
                        }
                    })
                }
                // Return the response that has been expanded with the subcategories of each category
                // console.log(response)
                return of(response);
            })
        );
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
