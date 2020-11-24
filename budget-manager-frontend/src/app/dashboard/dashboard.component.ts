import { Component, OnInit } from '@angular/core';
import { BudgetService } from '../budget.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  budgetService: BudgetService;

  constructor() { }

  ngOnInit(): void {
  }

  add(description: string, amount: string, isIncome: boolean): void {
    let newAmount: number = Number.parseFloat(amount);
    console.log(typeof isIncome);
    //testing code
    console.log("" + newAmount + " " + description);
    //end testing code

  }
}
