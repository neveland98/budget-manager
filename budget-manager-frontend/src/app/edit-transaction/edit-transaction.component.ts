import { Route } from '@angular/compiler/src/core';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BudgetService } from '../budget.service';
import { TokenStorageService } from '../token-storage.service';
import { Transaction } from '../transaction';

@Component({
  selector: 'app-edit-transaction',
  templateUrl: './edit-transaction.component.html',
  styleUrls: ['./edit-transaction.component.css']
})
export class EditTransactionComponent implements OnInit {
  dateString: string;
  @Input() transaction: Transaction;
  constructor(private budgetService: BudgetService, private router:Router, private tokenStorage:TokenStorageService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getTransaction();
  }
  getTransaction(): void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.budgetService.getTransaction(id).subscribe(transaction=>{
      this.transaction = transaction;
    });
  }
  update(dateString: string): void {
    dateString+="T00:00:00";
    this.transaction.date = new Date(dateString);//this is the hackiest fix i've ever done in my life and I hope I never have to do anything like this again.
    this.budgetService.updateTransaction(this.transaction).subscribe(()=>this.router.navigate(['transactions']));
  }
}
