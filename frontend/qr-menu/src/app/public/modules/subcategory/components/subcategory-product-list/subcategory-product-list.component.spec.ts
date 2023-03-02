import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubcategoryProductListComponent } from './subcategory-product-list.component';

describe('SubcategoryProductListComponent', () => {
  let component: SubcategoryProductListComponent;
  let fixture: ComponentFixture<SubcategoryProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubcategoryProductListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubcategoryProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
