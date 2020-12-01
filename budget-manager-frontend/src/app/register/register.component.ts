import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { RegisterRequest } from '../register-request';
import { TokenStorageService } from '../token-storage.service';

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
  confirmPassword: string = "";
  constructor(private tokenStorage: TokenStorageService, private auth: AuthorizationService, private router: Router) { }

  ngOnInit(): void {
    if(this.tokenStorage.getUser()) this.router.navigate(['dashboard']);
  }
  onSubmit():void{
    if(this.confirmPassword !== this.registData.password){
      alert("Passwords do not match!");
      return;
    }
    this.auth.register(this.registData).subscribe(message=>console.log(message));
    this.router.navigate(['login']);
  }

  register() {}
}
