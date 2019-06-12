import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ToplistService {

  toppList = 'innan call';

  constructor(private http: HttpClient) { }


  getTopList(cb: (data: any) => void) {
    this.http.get('http://192.168.1.11:8080/clock').subscribe(
      data => {
        console.log(data);
       // cb(JSON.toString(data));
        cb(data);
      },
      error => {
        console.log(error);
        this.toppList = 'det blev lite fel :(';
        cb('DET BLEV JU FELLL!');
      });
  }
}
