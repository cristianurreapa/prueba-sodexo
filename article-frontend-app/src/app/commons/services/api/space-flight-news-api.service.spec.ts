import { TestBed } from '@angular/core/testing';

import { SpaceFlightNewsApiService } from './space-flight-news-api.service';

describe('SpaceFlightNewsApiService', () => {
  let service: SpaceFlightNewsApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpaceFlightNewsApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
