import { NoopScrollStrategy } from '@angular/cdk/overlay';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Suggestion } from 'src/app/models/suggestion.model';
import { BaseTableComponent } from '../../shared/dashboard-table/base-table.component';
import { EditSuggestionViewComponent } from '../edit-suggestion-view/edit-suggestion-view.component';
import { NewSuggestionViewComponent } from '../new-suggestion-view/new-suggestion-view.component';
import { SuggestionDashboardService } from '../suggestion-dashboard.service';
import { SuggestionInfoViewComponent } from '../suggestion-info-view/suggestion-info-view.component';

@Component({
    selector: 'app-suggestion-table',
    templateUrl: '../../shared/dashboard-table/base-table.component.html',
    styleUrls: ['../../shared/dashboard-table/base-table.component.scss']
})
export class SuggestionTableComponent extends BaseTableComponent {

    constructor(
        public override service: SuggestionDashboardService,
        public override dialog: MatDialog ) {

        super(service, dialog);

        this.embeddedList = 'suggestionList';
        this.type = 'Suggestion';
        this.plural = 'Suggestions';
    }

     // Edit the entity
     override editEntity(suggestion: Suggestion): void {
        this.dialog.open(EditSuggestionViewComponent, {
            data: suggestion,
            scrollStrategy: new NoopScrollStrategy()
        });
    }

    // Add a new entity
    override createEntity(): void {
        let newEntity: Suggestion;
        const dialogRef = this.dialog.open(NewSuggestionViewComponent, {
            data: newEntity,
            scrollStrategy: new NoopScrollStrategy()
        });
        dialogRef.afterClosed().subscribe(result => {
            // Ignore when the dialog was closed by canceling
            if (result) {
                // Add the created product to the table
                this.dataSource.data.push(result);
                this.dataSource.data = this.dataSource.data;
            }
        });
    }

    // Open a specific infoComponent depending on the parameter
    override showEntityInfo(suggestion: Suggestion): void {
        this.showInfo(suggestion, SuggestionInfoViewComponent);
    }
}
