import { TestBed } from '@angular/core/testing';

import { SubcategoryDashboardService } from './subcategory-dashboard.service';

describe('SubcategoryDashboardService', () => {
  let service: SubcategoryDashboardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubcategoryDashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
