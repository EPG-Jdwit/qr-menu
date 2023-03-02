import { Component, Input } from '@angular/core';
import { of, from } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Product, SubcategoryProductList } from '../../../product/product.model';
import { SubcategoryProductListService } from './subcategory-product-list.service';

@Component({
  selector: 'app-subcategory-product-list',
  templateUrl: './subcategory-product-list.component.html',
  styleUrls: ['./subcategory-product-list.component.scss']
})
export class SubcategoryProductListComponent {
  products: Product[] = [];
  @Input() categoryId: number;
  @Input() subcategoryId: number;

  constructor(private subcategoryProductService: SubcategoryProductListService) {}

  ngOnInit() {
    const resultList: Product[] = [];

    this.subcategoryProductService.getSubcategoryProducts(this.categoryId, this.subcategoryId)
      .pipe(this.unwrapSubcategoryProducts()).subscribe(
        subcategoryProduct => {
          resultList.push(subcategoryProduct)
        });
        this.products = resultList;
  }

  private unwrapSubcategoryProducts(){
    return mergeMap((response: SubcategoryProductList<Product>) => {
      if (!response || !response._embedded) {
        return []; // TODO error
      }
      return from(response._embedded.productList).pipe(
        mergeMap(product => {
          return of({
              id: product.id,
              name: product.name,
              price: product.price,
              description: product.description,
              allergenics: product.allergenics
          } as Product);
          })
        )
    });
  }
}
