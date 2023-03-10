import { Component, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-sub-edit-product-view',
    templateUrl: './sub-edit-product-view.component.html',
    styleUrls: ['./sub-edit-product-view.component.scss']
})
export class SubEditProductViewComponent {
    @Input() entityForm: FormGroup;
    @Input() allergenicFormControl: FormControl;
    // TODO: Add endpoint to the backend to retrieve this list dynamically
    allergenicList: string[] = [ "Ei", "Melk", "Gluten", "Lupine", "Mosterd", "Noten", "Pindas",
        "Schaaldieren", "Selderij", "Sesamzaad", "Soja", "Vis", "Weekdieren", "Zwavel"].sort();
    


    get price() {
        return this.entityForm.get("price");
    }

    getPriceErrorMessage(): string {
            return this.entityForm.get("price").hasError('min') ? 'Price must be >= 0' : '';
        }
}
