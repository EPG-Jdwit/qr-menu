import { Component, Inject } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Product } from 'src/app/public/modules/product/product.model';
import { ProductTableService } from '../product-table/product-table.service';

@Component({
  selector: 'app-product-edit-view',
  templateUrl: './product-edit-view.component.html',
  styleUrls: ['./product-edit-view.component.scss']
})
export class ProductEditViewComponent {
  allergenics = new FormControl('');
  allergenicList: string[] = ['Milk', 'Egg', 'Seafood', 'Gluten', 'Nuts', 'Soja'];

  productForm = new FormGroup({
    name: new FormControl('', [
      // Validators.minLength(5)
    ]),
    price: new FormControl('', [
      Validators.min(0)
    ]),
    description: new FormControl('', [

    ])
  })

  constructor(
    public dialogRef: MatDialogRef<ProductEditViewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Product,
    private productService : ProductTableService
  ) {}

  // Simply close the dialog
  close(): void {
    this.dialogRef.close();
  }

  // Save the changes to the backend
  saveChanges(id: number): void {
    this.copyChanges();
    this.productService.updateProduct(id, this.data);
  }

  private copyChanges(): void {
    if (this.productForm.value.name) {
      this.data.name = this.productForm.value.name;
    }
    if (this.productForm.value.price) {
      this.data.price = +this.productForm.value.price;
    }
    if (this.productForm.value.description) {
      this.data.description = this.productForm.value.description;
    }
  }

  // Method to retrieve the price member of the FormGroup
  get price() {
    return this.productForm.get("price");
  }

  // Error messages
  getPriceErrorMessage(): string {
    return this.productForm.get("price").hasError('min') ? 'Price must be >= 0' : '';
  }
}
