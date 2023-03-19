import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSuggestionViewComponent } from './new-suggestion-view.component';

describe('NewSuggestionViewComponent', () => {
  let component: NewSuggestionViewComponent;
  let fixture: ComponentFixture<NewSuggestionViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewSuggestionViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewSuggestionViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
