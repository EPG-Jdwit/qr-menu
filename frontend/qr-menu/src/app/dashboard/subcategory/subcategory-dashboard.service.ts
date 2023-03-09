import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


import { AbstractDashboardService } from '../shared/abstract-dashboard.service';

@Injectable({
  providedIn: 'root'
})
export class SubcategoryDashboardService extends AbstractDashboardService {

  constructor(private http: HttpClient) { 
    super();
    this.setUrl('');
  }

  getAll(): Observable<any> {
    return null;
  }

  deleteById(id : number) : void {
    
  }

  editById(id: number, product: any) : void {
    
  }

  create(product: any) : Observable<any> {
    return null;
  }
}
