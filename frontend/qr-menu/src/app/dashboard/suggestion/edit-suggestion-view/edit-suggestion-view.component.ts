import { Component, Inject } from '@angular/core';
import { Validators, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Suggestion } from 'src/app/models/suggestion.model';
import { DashboardEditComponent } from '../../shared/dashboard-edit-dialog/dashboard-edit.component';
import { SuggestionDashboardService } from '../suggestion-dashboard.service';

@Component({
    selector: 'app-edit-suggestion-view',
    templateUrl: '../../shared/dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../../shared/dashboard-dialog/dashboard-dialog.component.scss']
})
export class EditSuggestionViewComponent extends DashboardEditComponent{
    constructor(
        public override dialogRef: MatDialogRef<EditSuggestionViewComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Suggestion,
        protected override service : SuggestionDashboardService
    ) {
        super(dialogRef, data, service);

        this.entityForm.addControl('url', new FormControl(data.url, [Validators.minLength(5)]));
        this.entityForm.addControl('description', new FormControl(data.description, [Validators.minLength(5)]));
    }

        // Copy values from the form into the data Suggestion object
        protected override copyChanges(): void {
            if (this.entityForm.value.name) {
                this.data.name = this.entityForm.value.name;
              }
              if (this.entityForm.value.url) {
                this.data.url = this.entityForm.value.url;
              }
              if (this.entityForm.value.description) {
                this.data.description = this.entityForm.value.description;
              }
        }
    
        override isType(type: string): boolean {
            return type === "Suggestion";
        }
}
