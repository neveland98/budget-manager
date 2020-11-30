import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BudgetService } from '../budget.service';
import { Category } from '../category';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {
  categories: Category[];
  constructor(private budgetService: BudgetService, private route: ActivatedRoute,private tokenStorage: TokenStorageService, private router: Router) { }
  newCategory: Category = {
    categoryId: null,
    categoryName: "",
    user_id: this.tokenStorage.getUser().id
  }
  ngOnInit(): void {
    this.getCategories();
    
  }
  createNewCategory():void {
    this.budgetService.createNewCategory(this.newCategory).subscribe(newCategory=>{
      this.categories.push(newCategory);
      this.newCategory.categoryName = "";
    });
  }
  edit(id: number):void {
    this.router.navigate([`category/${id}`]);
  }
  delete(id: number):void {
    this.budgetService.deleteCategory(id).subscribe(_=>this.categories = this.categories.filter(c => c.categoryId !== id));
  }
  getCategories():void {
    this.budgetService.getCategories(this.tokenStorage.getUser().id).subscribe(categories=>this.categories=categories);
  }
}
