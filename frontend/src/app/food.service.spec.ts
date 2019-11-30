import { TestBed } from '@angular/core/testing';

import { FoodService } from './foods/food.service';

describe('FoodService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FoodService = TestBed.get(FoodService);
    expect(service).toBeTruthy();
  });
});
