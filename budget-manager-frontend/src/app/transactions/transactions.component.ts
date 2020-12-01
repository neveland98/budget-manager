import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction';
import { BudgetService } from '../budget.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  transactions: Transaction[];
  // id: number;

  constructor(private budgetService: BudgetService, private route: ActivatedRoute,private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if(!this.tokenStorage.getUser()) {
      this.router.navigate(['login']);
      return;
    }
    else this.getTransactions();
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

  edit(transactionId: number): void {
    this.router.navigate([`transaction/${transactionId}`]);
  }
}
