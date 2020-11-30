import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BudgetService } from '../budget.service';
import { Category } from '../category';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.css']
})
export class EditCategoryComponent implements OnInit {
  @Input() category: Category;
  constructor(private budgetService: BudgetService, private router:Router, private tokenStorage:TokenStorageService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getCategory();
  }
  getCategory():void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.budgetService.getCategory(id).subscribe(category=>this.category=category);
  }
  update():void {
    this.budgetService.updateCategory(this.category).subscribe();
    this.router.navigate(['categories']);
  }
}
