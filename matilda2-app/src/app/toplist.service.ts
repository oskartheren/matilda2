import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToplistService {

  constructor() { }

  getTopList(): string {
    return 'Tre i tamp!';
  }
}
