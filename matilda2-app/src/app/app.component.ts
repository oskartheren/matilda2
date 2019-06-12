import { Component } from '@angular/core';
import { ToplistService } from './toplist.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'matilda2-app';

  constructor(toplistService: ToplistService) {
    this.title = toplistService.getTopList();
  }

}
