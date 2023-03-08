import { Component, Inject } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Product } from 'src/app/public/modules/product/product.model';
import { ProductTableService } from '../product-table/product-table.service';

@Component({
  selector: 'app-new-product-view',
  templateUrl: './new-product-view.component.html',
  styleUrls: ['./new-product-view.component.scss']
})
export class NewProductViewComponent {
  allergenicFormControl = new FormControl();
  // TODO: Add endpoint to the backend to retrieve this list dynamically
  allergenicList: string[] = [ "Ei", "Melk", "Gluten", "Lupine", "Mosterd", "Noten", "Pindas",
    "Schaaldieren", "Selderij", "Sesamzaad", "Soja", "Vis", "Weekdieren", "Zwavel"].sort();

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

  // Simply close the dialog
  close(): void {
    this.dialogRef.close();
  }

  // Save the new product
  saveChanges(): void {
    this.copyChanges();
    // Add the selectet allergenics to the product
    if (this.allergenicFormControl.value) {
      this.data.allergenicList = this.allergenicFormControl.value.map((a : string) => a.toLowerCase());
    }
    // Save the product to the backend
    this.productService.saveProduct(this.data).subscribe(response =>
      // Close the dialog and return the product (with assigned ID) front the backend
      this.dialogRef.close(response)
    );
  }

  // Copy values from the form into the data Product object
  private copyChanges(): void {
    this.data = {name: this.productForm.value.name, price: +this.productForm.value.price, description: this.productForm.value.description};
  }

  // Methods to retrieve members of the FormGroup
  get name() {
    return this.productForm.get("name");
  }

  get price() {
    return this.productForm.get("price");
  }

  // Error messages
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
