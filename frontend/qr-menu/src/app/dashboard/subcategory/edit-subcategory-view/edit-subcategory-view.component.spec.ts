import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSubcategoryViewComponent } from './edit-subcategory-view.component';

describe('EditSubcategoryViewComponent', () => {
  let component: EditSubcategoryViewComponent;
  let fixture: ComponentFixture<EditSubcategoryViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditSubcategoryViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditSubcategoryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
