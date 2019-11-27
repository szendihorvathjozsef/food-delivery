import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FoodInfoDialogComponent } from './food-info-dialog.component';

describe('FoodInfoDialogComponent', () => {
  let component: FoodInfoDialogComponent;
  let fixture: ComponentFixture<FoodInfoDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FoodInfoDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodInfoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
