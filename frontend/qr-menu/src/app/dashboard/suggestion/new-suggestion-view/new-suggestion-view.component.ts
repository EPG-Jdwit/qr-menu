import { Component, Inject } from '@angular/core';
import { Validators, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Suggestion } from 'src/app/models/suggestion.model';
import { DashboardNewComponent } from '../../shared/dashboard-new-dialog/dashboard-new.component';
import { SuggestionDashboardService } from '../suggestion-dashboard.service';

@Component({
    selector: 'app-new-suggestion-view',
    templateUrl: '../../shared/dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../../shared/dashboard-dialog/dashboard-dialog.component.scss']
})
export class NewSuggestionViewComponent extends DashboardNewComponent {
    constructor(
        public override dialogRef: MatDialogRef<NewSuggestionViewComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Suggestion,
        protected override service : SuggestionDashboardService
    ) {
        super(dialogRef, data, service);

        this.entityForm.addControl('url', new FormControl('', [Validators.required, Validators.minLength(3)]));
        this.entityForm.addControl('description', new FormControl('', [Validators.required, Validators.minLength(5)]));
    }
    
    
    // Copy values from the form into the data Suggestion object
    protected override copyChanges(): void {
        this.data = {
            name: this.entityForm.value.name,
            url: this.entityForm.value.url,
            description: this.entityForm.value.description
        };
    }

    override isType(type: string): boolean {
        return type === "Suggestion";
    }
}
