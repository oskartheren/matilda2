import { Component, OnInit } from '@angular/core';
import { ToplistService } from './toplist.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'matilda2-app';
  toplistService: ToplistService;

  ngOnInit(): void {
    let self = this;
    this.toplistService.getTopList(function(data) {
      console.log(data.topList);
      self.title = data.topList[0].userId;
    });
  }

  constructor(toplistService: ToplistService) {
    this.toplistService = toplistService;
  }

}
