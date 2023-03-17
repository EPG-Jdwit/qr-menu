import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubcategorySubViewComponent } from './subcategory-sub-view.component';

describe('EditSubcategorySubViewComponent', () => {
  let component: SubcategorySubViewComponent;
  let fixture: ComponentFixture<SubcategorySubViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubcategorySubViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubcategorySubViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
