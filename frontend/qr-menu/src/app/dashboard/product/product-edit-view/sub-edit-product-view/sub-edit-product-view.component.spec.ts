import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubEditProductViewComponent } from './sub-edit-product-view.component';

describe('SubEditProductViewComponent', () => {
  let component: SubEditProductViewComponent;
  let fixture: ComponentFixture<SubEditProductViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubEditProductViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubEditProductViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
