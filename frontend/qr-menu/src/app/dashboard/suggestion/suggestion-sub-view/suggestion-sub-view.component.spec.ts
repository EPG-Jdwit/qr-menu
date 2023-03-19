import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuggestionSubViewComponent } from './suggestion-sub-view.component';

describe('SuggestionSubViewComponent', () => {
  let component: SuggestionSubViewComponent;
  let fixture: ComponentFixture<SuggestionSubViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuggestionSubViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuggestionSubViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
