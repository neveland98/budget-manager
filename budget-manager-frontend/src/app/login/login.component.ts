import { Component, OnInit } from '@angular/core';
import { BudgetService } from '../budget.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private budgetService: BudgetService) { }

  ngOnInit(): void {
  }

  login(username: string,password: string): void {
    this.budgetService.login(username,password);
    
  }
}
