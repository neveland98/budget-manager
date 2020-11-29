import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction';
import { BudgetService } from '../budget.service';
import { ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  transactions: Transaction[];
  // id: number;

  constructor(private budgetService: BudgetService, private route: ActivatedRoute,private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    // this.id = Number.parseInt(this.route.snapshot.paramMap.get('id'));
    this.getTransactions();
  }

  getTransactions(): void {
    this.budgetService.getTransactions(this.tokenStorage.getUser().id).subscribe(
      transactions=>
      this.transactions = transactions
      );
  }

  delete(id: number): void {
    this.transactions = this.transactions.filter(t => t.transactionId !== id);
    this.budgetService.deleteTransaction(id).subscribe();
  }
}
