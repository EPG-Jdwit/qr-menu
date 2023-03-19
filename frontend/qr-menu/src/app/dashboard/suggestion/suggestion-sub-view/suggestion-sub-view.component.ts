import { Component, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-suggestion-sub-view',
    templateUrl: './suggestion-sub-view.component.html',
    styleUrls: ['./suggestion-sub-view.component.scss']
})
export class SuggestionSubViewComponent {
    @Input() entityForm: FormGroup;

    get url() {
        return this.entityForm.get("url");
    }

    get description() {
        return this.entityForm.get("description");
    }

    getUrlErrorMessage(): string {
        if (this.entityForm.get("url").hasError('required')) {
            return "URL is required";
        }
        return this.entityForm.get("url").hasError('minlength') ? "URL must be longer than 5 characters" : '';
    }

    getDescriptionErrorMessage(): string {
        if (this.entityForm.get("description").hasError('required')) {
            return "Description is required";
        }

        return this.entityForm.get("description").hasError('minlength') ? "Description must be longer than 5 characters" : '';
    }
}
