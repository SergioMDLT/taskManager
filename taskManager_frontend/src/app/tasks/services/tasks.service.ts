import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, throwError } from 'rxjs';
import { Task } from '../models/task';
import { environment } from '../../../environments/environment';


@Injectable({ providedIn: 'root' })
export class TasksService {

  private readonly appUrl: string = environment.apiUrl;

  constructor( private readonly http: HttpClient ) { }

  getTasks( params?: { completed?: boolean; id?: number; title?: string, page?: number; size?: number; sort?: string } ): Observable<any> {
    return this.http.get<any>( this.appUrl, { params } ).pipe(
      catchError(( error ) => {
        console.error( 'Error fetching tasks:', error );
        return of({
          content: [],
          totalPages: 0,
          totalElements: 0
        });
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
    return this.http.put<Task>( `${ this.appUrl }/${ id }`, {})
      .pipe(
        catchError( error => throwError(() => new Error( 'Failed to update task' ))),
      );
  }

  updateTaskPriority ( id: number, newPriority: number ): Observable<void> {
    return this.http.patch<void>( `${ this.appUrl }/${ id }/priority`, null, {
      params: { newPriority: newPriority }
    }).pipe(
      catchError(( error ) => {
        console.error( 'Error updating task priority:', error );
        throw error;
      })
    );
  }

}
