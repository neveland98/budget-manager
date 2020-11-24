import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { TransactionsComponent } from './transactions/transactions.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'dashboard/:id', component: DashboardComponent },
  {path: 'transactions/:id', component: TransactionsComponent},
  { path: '', redirectTo: '/dashboard/:id', pathMatch: 'full'},
  { path: "**", redirectTo: '/dashboard/:id'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }