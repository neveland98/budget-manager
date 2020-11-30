import { Component, OnInit } from '@angular/core';
import { BudgetService } from '../budget.service';
import { Transaction } from '../transaction';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from '../token-storage.service';
import { Category } from '../category';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  categories: Category[];
  selectedCategory: Category = {
    categoryId: 0,
    categoryName: "",
    user_id: this.tokenStorage.getUser().id
  }
  //id: number;
  private sub: any;//todo: figure out if this does anything
  constructor(private budgetService: BudgetService,private route: ActivatedRoute,private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if(!this.tokenStorage.getUser()) this.router.navigate(['login']);
    this.getCategories();
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
        date: new Date(dateString),
        category: this.selectedCategory
      } as Transaction
      ).subscribe();
  }
  signOut() {
    this.tokenStorage.signOut();
    this.router.navigate(['login']);
  }

  getCategories():void {
    this.budgetService.getCategories(this.tokenStorage.getUser().id).subscribe(categories=>this.categories=categories);
  }
}
