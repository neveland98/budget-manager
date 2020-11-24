import { Component, OnInit } from '@angular/core';
import { BudgetService } from '../budget.service';
import { Transaction } from '../transaction';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  id: number;
  private sub: any;

  constructor(private budgetService: BudgetService,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = Number.parseInt(this.route.snapshot.paramMap.get('id'));
  }

  add(description: string, amount: string, isIncome: boolean): void {
    let newAmount: number = Number.parseFloat(amount);
    description = description.trim();
    if(!description) return;
    this.budgetService.addTransaction(
      {transactionId: null, userId: this.id, description: description, charge: !isIncome, amount: newAmount} as Transaction
      ).subscribe();
  }
}
