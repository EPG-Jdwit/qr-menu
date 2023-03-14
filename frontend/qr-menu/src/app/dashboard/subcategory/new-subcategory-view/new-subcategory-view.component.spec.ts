import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSubcategoryViewComponent } from './new-subcategory-view.component';

describe('NewSubcategoryViewComponent', () => {
  let component: NewSubcategoryViewComponent;
  let fixture: ComponentFixture<NewSubcategoryViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewSubcategoryViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewSubcategoryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
