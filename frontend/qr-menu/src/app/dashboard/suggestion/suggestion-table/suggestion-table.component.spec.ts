import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuggestionTableComponent } from './suggestion-table.component';

describe('SuggestionTableComponent', () => {
  let component: SuggestionTableComponent;
  let fixture: ComponentFixture<SuggestionTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuggestionTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuggestionTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
