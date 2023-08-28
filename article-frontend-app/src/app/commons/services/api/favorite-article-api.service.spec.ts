import { TestBed } from '@angular/core/testing';

import { FavoriteArticleApiService } from './favorite-article-api.service';

describe('FavoriteArticleApiService', () => {
  let service: FavoriteArticleApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FavoriteArticleApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
