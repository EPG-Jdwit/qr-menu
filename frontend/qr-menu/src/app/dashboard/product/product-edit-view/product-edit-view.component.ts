import { Component, Inject } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import  {FloatLabelType } from '@angular/material/form-field';

import { Product } from 'src/app/public/modules/product/product.model';
import { ProductTableService } from '../product-table/product-table.service';

@Component({
  selector: 'app-product-edit-view',
  templateUrl: './product-edit-view.component.html',
  styleUrls: ['./product-edit-view.component.scss']
})
export class ProductEditViewComponent {
  floatLabelControl = new FormControl('always' as FloatLabelType);
  allergenics = new FormControl('');
  allergenicList: string[] = ['Milk', 'Egg', 'Seafood', 'Gluten', 'Nuts', 'Soja'];

  productForm = new FormGroup({
    name: new FormControl('', [
      // Validators.minLength(5)
    ]),
    price: new FormControl('', [

    ]),
    description: new FormControl('', [

    ])
  })

  constructor(
    public dialogRef: MatDialogRef<ProductEditViewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Product,
    private productService : ProductTableService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  saveChanges(id: number): void {
    this.copyChanges();
    this.productService.updateProduct(id, this.data);
  }

  getFloatLabelValue(): FloatLabelType {
    return this.floatLabelControl.value || 'auto';
  }

  private copyChanges(): void {
    if (this.productForm.value.name) {
      this.data.name = this.productForm.value.name;
    }
    if (this.productForm.value.price) {
      this.data.price = +this.productForm.value.price;
    }
    if (this.productForm.value.name) {
      this.data.description = this.productForm.value.description;
    }
  }
}
