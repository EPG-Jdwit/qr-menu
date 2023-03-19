import { TestBed } from '@angular/core/testing';

import { SuggestionDashboardService } from './suggestion-dashboard.service';

describe('SuggestionDashboardService', () => {
  let service: SuggestionDashboardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SuggestionDashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
