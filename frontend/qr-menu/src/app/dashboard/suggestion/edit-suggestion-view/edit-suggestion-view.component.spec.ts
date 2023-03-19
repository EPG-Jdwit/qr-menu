import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSuggestionViewComponent } from './edit-suggestion-view.component';

describe('EditSuggestionViewComponent', () => {
  let component: EditSuggestionViewComponent;
  let fixture: ComponentFixture<EditSuggestionViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditSuggestionViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditSuggestionViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
