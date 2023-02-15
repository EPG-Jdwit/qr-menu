import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { of, from } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Category } from '../../category.model';
import { CategoryService } from '../../category.service';

@Component({
  selector: 'app-category-content',
  templateUrl: './category-content.component.html',
  styleUrls: ['./category-content.component.scss']
})
export class CategoryContentComponent {
  category: Category;

  private subcategoriesUrl : string;

  constructor(
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getCategory();
  }

  getCategory(): void {
    const id = Number(this.route.snapshot.paramMap.get('categoryId'));
    this.subcategoriesUrl = "http://localhost:8081/categories/" + id + "/subcategories"
    this.categoryService.getCategoryById(id).subscribe(data => {
      this.category = data;
    })
  }
}
