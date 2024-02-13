import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificationRequest } from '../model/notification.request';
import { NotificationResponse} from '../model/notification.response';
import { environment } from '../assets/environment';


@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http: HttpClient) { }

  sendMessage(formData: NotificationRequest): Observable<NotificationResponse[]> {
    const response = this.http.post<NotificationResponse[]>(environment.apiUrl + '/messages/send', formData); 
    console.log(response);
    return response;
  }
}
