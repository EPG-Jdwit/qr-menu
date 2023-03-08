import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewProductViewComponent } from './new-product-view.component';

describe('NewProductViewComponent', () => {
  let component: NewProductViewComponent;
  let fixture: ComponentFixture<NewProductViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewProductViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewProductViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
