import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllergenAddComponent } from './allergen-add.component';

describe('AllergenAddComponent', () => {
  let component: AllergenAddComponent;
  let fixture: ComponentFixture<AllergenAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllergenAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllergenAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
