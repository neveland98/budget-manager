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
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.transactionsUrl)
    .pipe(tap(_ => console.log('fetched heroes')), catchError(this.handleError<Transaction[]>('getTransactions',[])));
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
