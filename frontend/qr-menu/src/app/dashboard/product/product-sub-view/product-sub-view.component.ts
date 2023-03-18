import { Component, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-product-sub-view',
    templateUrl: './product-sub-view.component.html',
    styleUrls: ['./product-sub-view.component.scss']
})
export class ProductSubViewComponent {
    @Input() entityForm: FormGroup;
    @Input() allergenicFormControl: FormControl;
    // TODO: Add endpoint to the backend to retrieve this list dynamically
    allergenicList: string[] = [ "Ei", "Melk", "Gluten", "Lupine", "Mosterd", "Noten", "Pindas",
        "Schaaldieren", "Selderij", "Sesamzaad", "Soja", "Vis", "Weekdieren", "Zwavel"].sort();
    
    get price() {
        return this.entityForm.get("price");
    }

    getPriceErrorMessage(): string {
        if (this.entityForm.get("price").hasError('required')) {
            return "Price is required";
        }
            return this.entityForm.get("price").hasError('min') ? 'Price must be >= 0' : '';
        }
}