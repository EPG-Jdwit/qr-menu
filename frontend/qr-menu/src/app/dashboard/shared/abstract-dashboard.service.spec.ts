import { TestBed } from '@angular/core/testing';

import { AbstractDashboardService } from './abstract-dashboard.service';

describe('AbstractDashboardService', () => {
  let service: AbstractDashboardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AbstractDashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
