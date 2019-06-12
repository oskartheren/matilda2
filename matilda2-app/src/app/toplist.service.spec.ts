import { TestBed } from '@angular/core/testing';

import { ToplistService } from './toplist.service';

describe('ToplistService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ToplistService = TestBed.get(ToplistService);
    expect(service).toBeTruthy();
  });
});
