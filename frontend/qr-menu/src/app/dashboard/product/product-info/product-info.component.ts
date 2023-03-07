import {Component, Inject} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { Product } from 'src/app/public/modules/product/product.model';

@Component({
  selector: 'app-product-info',
  templateUrl: './product-info.component.html',
  styleUrls: ['./product-info.component.scss']
})
export class ProductInfoComponent {
  constructor(
    public dialogRef: MatDialogRef<ProductInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Product,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
  editProduct(): void {
    console.log("edit");
  }
}
