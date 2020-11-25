import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction';
import { BudgetService } from '../budget.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  transactions: Transaction[];
  id: number;

  constructor(private budgetService: BudgetService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = Number.parseInt(this.route.snapshot.paramMap.get('id'));
    this.getTransactions();
  }

  getTransactions(): void {
    this.budgetService.getTransactions(this.id).subscribe(transactions=>this.transactions = transactions);
  }
}
