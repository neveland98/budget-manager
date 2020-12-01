import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { BudgetService } from '../budget.service';
import { LoginRequest } from '../login-request';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loggedIn: boolean = false;
  request: LoginRequest = {
    username: "",
    password: ""
  }
  errorMessage: string = "";
  constructor(private auth: AuthorizationService, private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if(this.tokenStorage.getUser()) this.router.navigate(['dashboard']);
  }
  login() {
    this.auth.login(this.request).subscribe(
      user =>
      {
          this.tokenStorage.saveUser(user);
          this.loggedIn = true;
          this.router.navigate(['dashboard']);
      }

      );

  }
  register() {
    this.router.navigate(['register']);
  }
  log(message: string) {
    this.errorMessage = message;
  }
  clearMessages() {
    this.errorMessage = "";
  }
}

