import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductSubViewComponent } from './product-sub-view.component';

describe('NewProductSubViewComponent', () => {
  let component: ProductSubViewComponent;
  let fixture: ComponentFixture<ProductSubViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductSubViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductSubViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
