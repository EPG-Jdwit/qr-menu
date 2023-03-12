import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable, MatTableDataSource } from '@angular/material/table';

import { Entity } from 'src/app/models/entity.model';
import { AbstractDashboardService } from '../abstract-dashboard.service';
import { DashboardInfoComponent } from '../dashboard-info/dashboard-info.component';
import { DeleteConfirmationComponent } from './delete-confirmation/delete-confirmation.component';

@Component({
  selector: 'dashboard-table',
  templateUrl: './base-table.component.html',
  styleUrls: ['./base-table.component.scss']
})
export class BaseTableComponent {
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;
    @ViewChild(MatTable) table!: MatTable<Entity>;
    dataSource: MatTableDataSource<Entity>;

    protected embeddedList: string = '';
    type: string = '';
    plural: string = '';

    displayedColumns: string[] = ['name', 'actions'];

    constructor(public service: AbstractDashboardService,
                public dialog: MatDialog) {
        this.dataSource = new MatTableDataSource();
    }

    // Fetch all existing entities for the table on initialization
    ngOnInit() {
        this.service.getAll().subscribe(
            entities => {
                // response only contains _embedded if entities exist
                if (entities._embedded) {
                    this.dataSource.data = eval('entities._embedded' + '.' + this.embeddedList);
                }
                // Set paginator, sorting and filtering to the data source
                this.dataSource.sort = this.sort;
                this.dataSource.paginator = this.paginator;
                this.dataSource.filterPredicate = function (entity, filter) {
                    return entity.name.toLowerCase().includes(filter.trim().toLowerCase())
                }
                // Set the MatTableDataSource as source of the table's data
                this.table.dataSource = this.dataSource;
            }
        )
    }

    // Filtering of the table by name (substring matching)
    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }

    // Delete the category
    deleteEntity(entity: Entity): void {

        const dialogRef = this.dialog.open(DeleteConfirmationComponent, {
            data: entity
        });

        dialogRef.afterClosed().subscribe(confirmation => {
            if (confirmation) {
                this.deleteConfirmed(entity);
            }
        })
    }

    // Delete the entity
    private deleteConfirmed(entity: Entity): void {
        // Delete from the backend
        this.service.deleteEntity(entity);
        // Delete from the frontend TODO: error handling NotFound
        const index = this.dataSource.data.findIndex(dataEntity => dataEntity.id == entity.id);
        this.dataSource.data.splice(index, 1);
        this.dataSource.data = this.dataSource.data;
    }

    // Display a dialog with all the information about the entity
    // with the posibility of editing it
    showEntityInfo(entity: Entity): void {
        const dialogRef = this.dialog.open(DashboardInfoComponent, {
            data: entity
        });
        dialogRef.afterClosed().subscribe(data => {
            // Ignore when the dialog was closed by canceling
            if (data) {
                // Open the edit dialog for the entity
                this.editEntity(data);
            }
        });
    }

    // Edit the entity
    editEntity(entity: Entity): void {
        // empty in base component
    }

    // Add a new entity
    createEntity(): void {
        // empty in base component
    }

    // Scrolls to the top of the table when the next page in the paginator requested
    onPaginateChange(event: any) {
        const element = document.getElementById("scroll-top");
        element.scrollIntoView({ behavior: "smooth", block: "end", inline: "nearest" });
        this.dataSource.data = this.dataSource.data;

    }

    // Synchronizes the two paginators
    public handlePageBottom(event: PageEvent) {
        this.paginator.pageSize = event.pageSize;
        this.paginator.pageIndex = event.pageIndex;
        this.paginator.page.emit(event);
    }

    // Inserts extra columns and sets actions as the last column
    public insertDisplayedColumn(column: string): void {
        var index = this.displayedColumns.indexOf("actions");
        this.displayedColumns[index] = column;
        this.displayedColumns.push("actions");
    }

}
