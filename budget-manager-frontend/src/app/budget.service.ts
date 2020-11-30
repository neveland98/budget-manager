import { Injectable } from '@angular/core';
import { Transaction } from './transaction';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BudgetService {
  private transactionsUrl = 'http://localhost:8080/api/transactions';
  private transactionUrl = 'http://localhost:8080/api/transaction';
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