import { Injectable } from '@angular/core';
import { Transaction } from './transaction';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Category } from './category';

@Injectable({
  providedIn: 'root'
})
export class BudgetService {
  private transactionsUrl = 'http://localhost:8080/api/transactions';
  private transactionUrl = 'http://localhost:8080/api/transaction';
  private categoriesUrl = 'http://localhost:8080/api/categories';
  private categoryUrl = 'http://localhost:8080/api/category';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  //Create
  addTransaction(transaction: Transaction): Observable<Transaction> {
    return this.http.post<Transaction>(this.transactionsUrl,transaction,this.httpOptions).pipe(
      tap((newTransaction: Transaction) => console.log(`added transaction with id: ${newTransaction.transactionId}`)),catchError(this.handleError<Transaction>("addTransaction"))
    )
  }
  //Read
  getTransactions(id: number): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.transactionsUrl+"/"+id)
    .pipe(tap(_ => console.log('fetched transactions')), catchError(this.handleError<Transaction[]>('getTransactions',[])));
  }
  getTransaction(transactionId: number): Observable<Transaction> {
    return this.http.get<Transaction>(this.transactionUrl+"/"+transactionId)
    .pipe(tap(_=>console.log(`fetched transaction with id: ${transactionId}`)), catchError(this.handleError<Transaction>('getTransaction',null)));
  }
  //Update
  updateTransaction(transaction: Transaction): Observable<any> {
    return this.http.put(this.transactionsUrl, transaction, this.httpOptions).pipe(
      tap(_ => console.log(`updated transaction id=${transaction.transactionId}`)),
      catchError(this.handleError<any>('updateTransaction'))
    );
  }
  //Delete
  deleteTransaction(id: number) {
    const url = `${this.transactionsUrl}/${id}`;
    return this.http.delete<Transaction>(url, this.httpOptions).pipe(
      tap(_ => console.log(`deleted transaction id=${id}`)),
      catchError(this.handleError<Transaction>('deleteTransaction'))
    );
  }
  login(username: string, password: string): void {
    console.log(username + " " + password);
  }

  createNewCategory(toAdd: Category): Observable<Category> {
    return this.http.post<Category>(this.categoriesUrl,toAdd,this.httpOptions)
    .pipe(tap((newCategory: Category) => console.log(`added category with id: ${newCategory.categoryId}`)),
    catchError(this.handleError<Category>("addCategory")));
  }
  getCategories(userId: number): Observable<Category[]> {
    return this.http.get<Category[]>("http://localhost:8080/api/categories/"+userId)
    .pipe(tap(_=>console.log('fetched categories')), catchError(this.handleError<Category[]>('getCategories',[])));
  }
  getCategory(categoryId: number): Observable<Category> {
    return this.http.get<Category>(this.categoryUrl+"/"+categoryId)
    .pipe(tap(_=>console.log(`fetched category with id: ${categoryId}`)),catchError(this.handleError<Category>("getCategory",null)));
  }
  updateCategory(updated: Category): Observable<any> {
    return this.http.put(this.categoriesUrl,updated,this.httpOptions)
    .pipe(tap(_=>console.log(`updated category with id: ${updated.categoryId}`)),catchError(this.handleError<any>("updateCategory",null)));
  }
  deleteCategory(categoryId: number): Observable<any> {
    return this.http.delete<Category>(this.categoryUrl+"/"+categoryId)
    .pipe(tap(_=>console.log(`deleted category with id: ${categoryId}`)),catchError(this.handleError<any>("deleteCategory")));
  }

  constructor(private http: HttpClient) { }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead


      // TODO: better job of transforming error for user consumption
      // this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}