import { Component, OnInit } from '@angular/core';
import { ToplistService } from './toplist.service';
import { NgForm } from '@angular/forms';
import { TopList } from './top-list'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  topList = [];
  toplistService: ToplistService;

  ngOnInit(): void {
    this.getTopList()
  }

  constructor(toplistService: ToplistService) {
    this.toplistService = toplistService
  }

  addToToplist(form: NgForm) {
    this.toplistService.addToToplist(form.value.userId).subscribe( data => console.debug(data),
        error => {
          console.error("Could not add to top list :(")
          console.error(error)
        }
      )
  }

  getTopList() {
    this.toplistService.getTopList().subscribe(
      (data: TopList) => {
        console.debug(data.topList)
        this.topList = data.topList
      },
      error => {
        console.error("Could not get top list :(")
        console.error(error)
      }
    )
  }
}
