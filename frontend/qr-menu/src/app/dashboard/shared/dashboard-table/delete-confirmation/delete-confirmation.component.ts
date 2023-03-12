import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'app-delete-confirmation',
    templateUrl: './delete-confirmation.component.html',
    styleUrls: ['./delete-confirmation.component.scss']
})
export class DeleteConfirmationComponent {
    constructor(
        public dialogRef: MatDialogRef<DeleteConfirmationComponent>,
        // TODO: data: Entity
        @Inject(MAT_DIALOG_DATA) public data: any,
      ) {}
    
    
    // Simply close the dialog
    close(): void {
        this.dialogRef.close();
    }
    
    // Save the changes to the backend
    confirm(): void {
        this.dialogRef.close(true);
    }
}
