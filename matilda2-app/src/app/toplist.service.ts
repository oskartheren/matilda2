import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ObservableInput } from 'rxjs';
import { log } from 'util';

@Injectable({
  providedIn: 'root'
})
export class ToplistService {

  private url = 'http://192.168.1.105:8080/clock';

  constructor(private http: HttpClient) {
  }

  getTopList(cb: (data: any) => void) {
    this.http.get(this.url).subscribe(
      data => {
        console.log(data);
        // cb(JSON.toString(data));
        cb(data);
      },
      error => {
        console.log(error);
        cb('DET BLEV JU FELLL!');
      });
  }

  addToToplist(userId: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    this.http.post(this.url, {content: userId}, httpOptions).subscribe(
      data => {
        console.log(data);
      },
      error => {
        console.error(error)
      }
    );
  }
}
