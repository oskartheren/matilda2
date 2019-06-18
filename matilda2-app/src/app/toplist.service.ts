import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToplistService {

  constructor(private http: HttpClient) { }

  getTopList(): Observable<Object> {
    return this.http.get('/clock')
  }

  addToToplist(userId: string): Observable<Object> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    return this.http.post('/clock', {content: userId}, httpOptions)
  }
}
