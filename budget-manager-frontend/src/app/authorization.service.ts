import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginResponse } from './login-response';
import { LoginRequest } from './login-request';
import { Observable } from 'rxjs';
import { RegisterRequest } from './register-request';
import { MessageResponse } from './message-response';

const options = {
  "headers":new HttpHeaders({
    "Content-Type": "application/json"
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  
  constructor(private client: HttpClient) { }
  register(request: RegisterRequest): Observable<any> {
    return this.client.post<MessageResponse>("http://localhost:8080/api/auth/signup",request,options);
  }

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.client.post<LoginResponse>("http://localhost:8080/api/auth/signin",request,options);
  }
}
