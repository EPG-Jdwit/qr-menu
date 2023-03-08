import { Component } from '@angular/core';
import { of, from } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Category, CategoryList } from '../../../../../models/category.model';
import { CategoryService } from '../../category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categoryList.component.html',
  styleUrls: ['./categoryList.component.scss']
})
export class CategoryListComponent {
  categories: Category[] = [];
  title: string = "Categorieën";

  constructor(private categoryService: CategoryService) {

  }

  ngOnInit() {
    const resultList: Category[] = [];

    this.categoryService.getCategories()
      .pipe(this.unwrapCategories()).subscribe(category => {
        resultList.push(category)
      });
    this.categories = resultList;
      
  }

  private unwrapCategories() {
    return mergeMap((response: CategoryList<Category>) => {
      if (!response || !response._embedded) {
        return []; // TODO error
      }
      return from(response._embedded.categoryList).pipe(
        mergeMap(category => {
            return of({
                id: category.id,
                name: category.name
            } as Category);
        })
    )
});
}
}