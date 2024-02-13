import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { SubmissionFormComponent } from './submission-form/submission-form.component';
import { LogHistoryComponent } from './log-history/log-history.component';
import { LogService } from '../services/log.service';
import { MessageService } from '../services/message.service';


@NgModule({
  declarations: [
    AppComponent,
    SubmissionFormComponent,
    LogHistoryComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    LogService ,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
