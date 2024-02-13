import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../services/message.service';
import { FormControl, FormGroup } from '@angular/forms';
import { NotificationResponse } from '../../model/notification.response';
import { LogService } from '../../services/log.service';

@Component({
  selector: 'app-submission-form',
  templateUrl: './submission-form.component.html',
  styleUrls: ['./submission-form.component.scss'],
  providers: [MessageService, LogService]
})
export class SubmissionFormComponent implements OnInit {

  public myForm!: FormGroup;
  public logData!: NotificationResponse[];

  constructor(private messageService: MessageService, private logService: LogService) { }
  ngOnInit(): void {
    this.myForm = new FormGroup({
      category: new FormControl(''),
      message: new FormControl('')
    })
    this.getLogData();
  }

  submitForm(): void {
    console.log(this.myForm.value)

    this.messageService.sendMessage(this.myForm.value)
      .subscribe( response => {
        this.getLogData();
      }, error => {
        console.error('Error sending message:', error);
      });
  }

  getLogData() {
    this.logService.getNotifications().subscribe(data => {
      // Sort data from newest to oldest
      this.logData = data.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
    });
  }
}
