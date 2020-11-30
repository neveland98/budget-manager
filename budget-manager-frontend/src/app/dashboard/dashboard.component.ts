import { Component, OnInit } from '@angular/core';
import { BudgetService } from '../budget.service';
import { Transaction } from '../transaction';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  //id: number;
  private sub: any;//todo: figure out if this does anything
  constructor(private budgetService: BudgetService,private route: ActivatedRoute,private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if(!this.tokenStorage.getUser()) this.router.navigate(['login']);
  }

  add(description: string, amount: string, isIncome: boolean,dateString: string): void {
    let newAmount: number = Math.floor(Number.parseFloat(amount)*100);
    let id = this.tokenStorage.getUser().id;
    description = description.trim();
    if(!description) return;
    dateString+="T00:00:00";//this is a bit hacky but we don't actually care about the time, and this makes the date correct
    this.budgetService.addTransaction(
      {transactionId: null,
        userId: id,
        description: description,
        charge: !isIncome,
        amount: newAmount,
        date: new Date(dateString)
      } as Transaction
      ).subscribe();
  }
  signOut() {
    this.tokenStorage.signOut();
    this.router.navigate(['login']);
  }
}
