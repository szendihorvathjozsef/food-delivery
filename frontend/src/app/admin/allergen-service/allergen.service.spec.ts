import { TestBed } from '@angular/core/testing';

import { AllergenService } from './allergen.service';

describe('AllergenService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AllergenService = TestBed.get(AllergenService);
    expect(service).toBeTruthy();
  });
});
