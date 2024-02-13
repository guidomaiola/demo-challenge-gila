import { Component, Input, OnInit } from '@angular/core';
import {NotificationResponse} from '../../model/notification.response';

@Component({
  selector: 'app-log-history',
  templateUrl: './log-history.component.html',
  styleUrls: ['./log-history.component.css'],
  providers: []

})
export class LogHistoryComponent implements OnInit {
  @Input()
  logData: NotificationResponse[] = [];

  constructor() { }

  ngOnInit(): void {
  }


}
