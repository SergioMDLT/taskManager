import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, delay, Observable, of } from 'rxjs';
import { Task } from '../models/task';

@Injectable({ providedIn: 'root' })
export class TasksService {

  private readonly appUrl: string = 'http://localhost:8080/tasks';

  constructor( private readonly http: HttpClient ) { }

  getPending(): Observable<Task[]> {
    const url = `${ this.appUrl }/pending`;
    return this.http.get<Task[]>( url )
      .pipe(
        catchError( error => of([])),
        delay( 500 ),
      );
  }

  getHistory(): Observable<Task[]> {
    return this.http.get<Task[]>( this.appUrl )
      .pipe(
        catchError( error => of([])),
        delay( 500 ),
      );
  }

  searchByTitle( title: string ): Observable<Task[]> {
    const url = `${ this.appUrl }/by-title/${ title }`; //cambiar la búsqueda en el back
    return this.http.get<Task[]>( url )
      .pipe(
          catchError( error => of([])),
          delay( 500 ),
      );
  }

}
