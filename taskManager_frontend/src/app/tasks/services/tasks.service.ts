import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, delay, Observable, of, throwError } from 'rxjs';
import { Task } from '../models/task';

@Injectable({ providedIn: 'root' })
export class TasksService {

  private readonly appUrl: string = 'http://localhost:8080/tasks';

  constructor( private readonly http: HttpClient ) { }

  getTasks( params?: { completed?: boolean; id?: number; title?: string } ): Observable<Task[]> {
    return this.http.get<Task[]>( this.appUrl, { params } ).pipe(
      catchError((error) => {
        console.error('Error fetching tasks:', error);
        return of([]);
      }),
    );
  }

  createTask( title: string, description: string ): Observable<Task> {
    const task = {
      title: title,
      description: description,
      completed: false
    };

    return this.http.post<Task>( this.appUrl, task )
      .pipe(
        catchError( error => throwError(() => new Error( 'Failed to create task' ))),
      );
  }

  deleteTask( id: number ): Observable<Task> {
    return this.http.delete<Task>( `${ this.appUrl }/${ id }` )
      .pipe(
        catchError( error => throwError(() => new Error( 'Failed to delete task' ))),
      );
  }

  updateTask( id: number ): Observable<Task> {
    return this.http.put<Task>(`${this.appUrl}/${id}`, {})
      .pipe(
        catchError(error => throwError(() => new Error('Failed to update task'))),
      );
  }


}
