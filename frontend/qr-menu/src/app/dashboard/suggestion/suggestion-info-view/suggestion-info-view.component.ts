import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Suggestion } from 'src/app/models/suggestion.model';
import { DashboardInfoComponent } from '../../shared/dashboard-info-dialog/dashboard-info.component';

@Component({
    selector: 'app-suggestion-info-view',
    templateUrl: './suggestion-info-view.component.html',
	styleUrls: ['../../shared/dashboard-info-dialog/dashboard-info.component.scss']
})
export class SuggestionInfoViewComponent extends DashboardInfoComponent {
    constructor(
		public override dialogRef: MatDialogRef<SuggestionInfoViewComponent>,
		// TODO: data: Category
		@Inject(MAT_DIALOG_DATA) public override data: Suggestion,
	  ) {
		super(dialogRef, data);
	  }
}
