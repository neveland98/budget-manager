import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction';
import { BudgetService } from '../budget.service';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  transactions: Transaction[];

  constructor(private budgetService: BudgetService) { }

  ngOnInit(): void {
    this.getTransactions();
  }

  getTransactions(): void {
    this.budgetService.getTransactions().subscribe(transactions=>this.transactions = transactions);
  }
}
