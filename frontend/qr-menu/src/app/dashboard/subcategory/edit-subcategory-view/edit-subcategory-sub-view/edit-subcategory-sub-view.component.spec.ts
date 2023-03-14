import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSubcategorySubViewComponent } from './edit-subcategory-sub-view.component';

describe('EditSubcategorySubViewComponent', () => {
  let component: EditSubcategorySubViewComponent;
  let fixture: ComponentFixture<EditSubcategorySubViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditSubcategorySubViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditSubcategorySubViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
