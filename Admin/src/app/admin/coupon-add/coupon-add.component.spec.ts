import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CouponAddComponent } from './coupon-add.component';

describe('CouponAddComponent', () => {
  let component: CouponAddComponent;
  let fixture: ComponentFixture<CouponAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CouponAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CouponAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
