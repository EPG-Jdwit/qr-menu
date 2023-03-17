import { Component, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

import { Category } from 'src/app/models/category.model';
import { Product } from 'src/app/models/product.model';
import { SubcategoryDashboardService } from '../../subcategory-dashboard.service';

@Component({
    selector: 'app-edit-subcategory-sub-view',
    templateUrl: '../../subcategory-sub-view/subcategory-sub-view.component.html',
    styleUrls: ['../../subcategory-sub-view/subcategory-sub-view.component.scss']
})
export class EditSubcategorySubViewComponent {
    @Input() entityForm: FormGroup;
    @Input() categoryFormControl: FormControl;
    @Input() productFormControl: FormControl;
    categoryList: Category[];
    filteredCategoryList: Category[];
    productList : Product[];
    filteredProductList: Product[]

    constructor(
        protected  service : SubcategoryDashboardService
    ) {
    }

    get category() {
        return this.entityForm.get("category");
    }

    getCategoryErrorMessage(): string {
        // if (this.entityForm.get("category").hasError('required')) {
            return "A category is required";
        // }
    }

    ngOnInit(): void {
        this.getAllCategories();
        this.getAllProducts();
    }

    private getAllCategories(): void {
        this.service.getCategories().subscribe(data => {
                this.categoryList = data._embedded.categoryList;
                this.filteredCategoryList = this.categoryList;
        });
    }

    filterCategories(value) {
        this.filteredCategoryList = this.searchCategories((value.target as HTMLInputElement).value);
    }

    searchCategories(value: string) {
        let filter = value.trim().toLowerCase();
        return this.categoryList.filter(item => item.name.trim().toLowerCase().includes(filter));
    }

    private getAllProducts(): void {
        this.service.getAllProducts().subscribe(data => {
            this.productList = data._embedded.productList;
            this.filteredProductList = this.productList;
        });
    }

    filterProducts(value) {
        this.filteredProductList = this.searchProducts((value.target as HTMLInputElement).value);
    }

    searchProducts(value: string) {
        let filter = value.trim().toLowerCase();
        return this.productList.filter(item => item.name.trim().toLowerCase().includes(filter));
    }

    compareObjects(o1: any, o2: any) {
        if(o1.name == o2.name && o1.id == o2.id )
        return true;
        else return false
      }

}