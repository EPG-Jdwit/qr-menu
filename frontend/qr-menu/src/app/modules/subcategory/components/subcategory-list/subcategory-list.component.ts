import { Component, Input } from '@angular/core';
import { of, from } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Subcategory, SubcategoryList } from '../../subcategory.model';
import { SubcategoryService } from '../../subcategory.service';

@Component({
  selector: 'app-subcategory-list',
  templateUrl: './subcategory-list.component.html',
  styleUrls: ['./subcategory-list.component.scss']
})
export class SubcategoryListComponent {
  subcategories: Subcategory[] = [];
  @Input() categoryId: number;

  constructor(private subcategoryService: SubcategoryService) {}

  ngOnInit() {
    const resultList: Subcategory[] = []

    this.subcategoryService.getSubcategories(this.categoryId)
      .pipe(this.unwrapSubcategories()).subscribe(subcategory => {
        resultList.push(subcategory)
      });
    this.subcategories = resultList;
  }

  private unwrapSubcategories() {
    return mergeMap((response: SubcategoryList<Subcategory>) => {
      if (!response || !response._embedded) {
        return []; // TODO error
      }

      return from(response._embedded.subcategoryList).pipe(
        mergeMap(subcategory => {
          return of({
              id: subcategory.id,
              name: subcategory.name
          } as Subcategory);
          })
        )
    });
  }
}
