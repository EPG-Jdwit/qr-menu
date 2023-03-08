import { TestBed } from '@angular/core/testing';

import { ProductDashboardService } from './product-dashboard.service';

describe('ProductDashboardService', () => {
  let service: ProductDashboardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductDashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
