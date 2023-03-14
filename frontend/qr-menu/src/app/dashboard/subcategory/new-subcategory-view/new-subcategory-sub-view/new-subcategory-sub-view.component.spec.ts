import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSubcategorySubViewComponent } from './new-subcategory-sub-view.component';

describe('NewSubcategorySubViewComponent', () => {
  let component: NewSubcategorySubViewComponent;
  let fixture: ComponentFixture<NewSubcategorySubViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewSubcategorySubViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewSubcategorySubViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
