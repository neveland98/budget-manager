import { Component, OnInit } from '@angular/core';
import { BudgetService } from '../budget.service';
import { Transaction } from '../transaction';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private budgetService: BudgetService) { }

  ngOnInit(): void {
  }

  add(description: string, amount: string, isIncome: boolean): void {
    let newAmount: number = Number.parseFloat(amount);
    //testing code
    
    //end testing code
    description = description.trim();
    if(!description) return;
    this.budgetService.addTransaction(
      {transactionId: null, userId: 1, description: description, charge: !isIncome, amount: newAmount} as Transaction
      ).subscribe();
  }
}
