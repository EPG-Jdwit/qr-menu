import { Component, Inject } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import  {FloatLabelType } from '@angular/material/form-field';

import { Product } from 'src/app/public/modules/product/product.model';
import { ProductTableService } from '../product-table/product-table.service';

@Component({
  selector: 'app-new-product-view',
  templateUrl: './new-product-view.component.html',
  styleUrls: ['./new-product-view.component.scss']
})
export class NewProductViewComponent {
  floatLabelControl = new FormControl('always' as FloatLabelType);
  allergenics = new FormControl('');
  allergenicList: string[] = ['Milk', 'Egg', 'Seafood', 'Gluten', 'Nuts', 'Soja'];

  productForm = new FormGroup({
    name: new FormControl('', [
      Validators.required
    ]),
    price: new FormControl('', [
      Validators.required,
      Validators.min(0)
    ]),
    description: new FormControl('', [

    ])
  })

  constructor(
    public dialogRef: MatDialogRef<NewProductViewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Product,
    private productService : ProductTableService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  saveChanges(): void {
    this.copyChanges();
    this.productService.saveProduct(this.data).subscribe(response =>
      this.dialogRef.close(response)
    );
  }

  getFloatLabelValue(): FloatLabelType {
    return this.floatLabelControl.value || 'auto';
  }

  private copyChanges(): void {
    this.data = {name: this.productForm.value.name, price: +this.productForm.value.price, description: this.productForm.value.description};
  }

  get name() {
    return this.productForm.get("name");
  }

  get price() {
    return this.productForm.get("price");
  }

  // // Errors
  getNameErrorMessage(): string{
    return 'Name is required';
  }

  getPriceErrorMessage(): string {
    if (this.productForm.get("price").hasError('required')) {
      return "Price is required";
    }
    return this.productForm.get("price").hasError('min') ? 'Price must be >= 0' : '';
  }
}
