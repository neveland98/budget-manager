import { Component, OnInit } from '@angular/core';
import { RegisterRequest } from '../register-request';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  loggedIn: boolean  = false;
  registData: RegisterRequest = {
    username: "",
    email: "",
    password: ""
  }
  constructor() { }

  ngOnInit(): void {
  }
  register() {}
}
