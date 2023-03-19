import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuggestionInfoViewComponent } from './suggestion-info-view.component';

describe('SuggestionInfoViewComponent', () => {
  let component: SuggestionInfoViewComponent;
  let fixture: ComponentFixture<SuggestionInfoViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuggestionInfoViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuggestionInfoViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
