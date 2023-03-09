import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewProductSubViewComponent } from './new-product-sub-view.component';

describe('NewProductSubViewComponent', () => {
  let component: NewProductSubViewComponent;
  let fixture: ComponentFixture<NewProductSubViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewProductSubViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewProductSubViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
