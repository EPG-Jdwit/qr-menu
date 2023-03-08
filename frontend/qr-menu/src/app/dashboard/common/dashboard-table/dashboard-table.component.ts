import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable, MatTableDataSource } from '@angular/material/table';

import { Category } from 'src/app/models/category.model';
import { CategoryDashboardService } from '../../category/category-dashboard.service';
import { AbstractDashboardService } from '../abstract-dashboard.service';

@Component({
  selector: 'dashboard-table',
  templateUrl: './dashboard-table.component.html',
  styleUrls: ['./dashboard-table.component.scss']
})
export class DashboardTableComponent {
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;
    @ViewChild(MatTable) table!: MatTable<Category>;
    dataSource: MatTableDataSource<Category>;

    protected embeddedList = ''

    displayedColumns = ['name', 'actions'];

    constructor(public service: AbstractDashboardService,
                public dialog: MatDialog) {
        this.dataSource = new MatTableDataSource();
    }

    // Fetch all existing categories for the table on initialization
    ngOnInit() {
        this.service.getAll().subscribe(
            items => {
                // response only contains _embedded if categories exist
                if (items._embedded) {
                    this.dataSource.data = eval('items._embedded' + '.' + this.embeddedList);
                }
                // Set paginator, sorting and filtering to the data source
                this.dataSource.sort = this.sort;
                this.dataSource.paginator = this.paginator;
                this.dataSource.filterPredicate = function (product, filter) {
                return product.name.toLowerCase().includes(filter.trim().toLowerCase())
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

    // Delete category
    deleteById(id: number): void {

    }

    // Display a dialog with all the information about the category
    // with the posibility of editing it
    showInfoById(id: number): void {

    }

    // Edit the category
    editById(id: number): void {

    }

    // Add a new category
    create(): void {

    }

    // Scrolls to the top of the table when the next page in the paginator requested
    onPaginateChange(event: any) {
        const element = document.getElementById("scroll-top");
        element.scrollIntoView({ behavior: "smooth", block: "end", inline: "nearest" });
    }

    public handlePageBottom(event: PageEvent) {
        this.paginator.pageSize = event.pageSize;
        this.paginator.pageIndex = event.pageIndex;
        this.paginator.page.emit(event);
    }

}
