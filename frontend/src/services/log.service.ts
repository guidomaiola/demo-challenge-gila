
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs'
import { environment } from '../assets/environment';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(private http: HttpClient) { }

  getNotifications(): Observable<any[]> {
    return this.http.get<any[]>(environment.apiUrl + '/notifications');
  }
}
