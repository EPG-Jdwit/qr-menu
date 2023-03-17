import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCategoryViewComponent } from './new-category-view.component';

describe('NewCategoryViewComponent', () => {
  let component: NewCategoryViewComponent;
  let fixture: ComponentFixture<NewCategoryViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCategoryViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewCategoryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});