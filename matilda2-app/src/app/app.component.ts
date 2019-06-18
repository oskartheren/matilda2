import { Component, OnInit } from '@angular/core';
import { ToplistService } from './toplist.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  topList = [];
  toplistService: ToplistService;

  ngOnInit(): void {
    let self = this;
    this.toplistService.getTopList(data => {
      console.log(data.topList);
      self.topList = data.topList;
    });
  }

  constructor(toplistService: ToplistService) {
    this.toplistService = toplistService
  }

  addToToplist(form: NgForm) {
    this.toplistService.addToToplist(form.value.userId)
  }
}
