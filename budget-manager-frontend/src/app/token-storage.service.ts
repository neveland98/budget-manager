import { Injectable } from '@angular/core';
import { LoginRequest } from './login-request';
import { LoginResponse } from './login-response';

const userKey = "budgetuser";

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  saveUser(user: LoginResponse):void {
    window.sessionStorage.removeItem(userKey);
    window.sessionStorage.setItem(userKey,JSON.stringify(user));
  }
  getUser():LoginResponse {
    return JSON.parse(window.sessionStorage.getItem(userKey));
  }
  signOut() {
    window.sessionStorage.clear();
  }
}
