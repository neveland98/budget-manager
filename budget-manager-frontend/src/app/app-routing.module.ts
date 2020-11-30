import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriesComponent } from './categories/categories.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EditCategoryComponent } from './edit-category/edit-category.component';
import { EditTransactionComponent } from './edit-transaction/edit-transaction.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { TransactionsComponent } from './transactions/transactions.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'dashboard', component: DashboardComponent },
  {path: 'transactions', component: TransactionsComponent},
  {path: 'transaction/:id', component: EditTransactionComponent},
  {path: 'categories', component: CategoriesComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'category/:id', component: EditCategoryComponent},
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: "**", redirectTo: 'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }