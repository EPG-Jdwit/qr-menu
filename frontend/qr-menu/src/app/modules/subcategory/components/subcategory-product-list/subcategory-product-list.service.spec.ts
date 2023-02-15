import { TestBed } from '@angular/core/testing';

import { SubcategoryProductListService } from './subcategory-product-list.service';

describe('SubcategoryProductListService', () => {
  let service: SubcategoryProductListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubcategoryProductListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
