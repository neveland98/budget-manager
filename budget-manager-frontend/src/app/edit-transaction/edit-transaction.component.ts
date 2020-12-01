import { Route } from '@angular/compiler/src/core';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BudgetService } from '../budget.service';
import { Category } from '../category';
import { TokenStorageService } from '../token-storage.service';
import { Transaction } from '../transaction';

@Component({
  selector: 'app-edit-transaction',
  templateUrl: './edit-transaction.component.html',
  styleUrls: ['./edit-transaction.component.css']
})
export class EditTransactionComponent implements OnInit {
  dateString: string;
  categories: Category[];
  @Input() transaction: Transaction;
  constructor(private budgetService: BudgetService, private router:Router, private tokenStorage:TokenStorageService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    if(!this.tokenStorage.getUser()) {
      this.router.navigate(['login']);
      return;
    }
    this.getTransaction();
    this.getCategories();
  }
  getTransaction(): void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.budgetService.getTransaction(id).subscribe(transaction=>{
      this.transaction = transaction;
    });
  }
  update(dateString: string): void {
    let blank = new RegExp('^\\s*$');
    if(blank.test(dateString)||blank.test(this.transaction.description)) return;
    dateString+="T00:00:00";
    this.transaction.date = new Date(dateString);
    this.budgetService.updateTransaction(this.transaction).subscribe(()=>this.router.navigate(['transactions']));
  }
  getCategories():void {
    this.budgetService.getCategories(this.tokenStorage.getUser().id).subscribe(categories=>{
      this.categories=categories;
    });
  }
}
