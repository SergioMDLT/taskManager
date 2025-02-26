import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable, of, switchMap, throwError } from 'rxjs';
import { Task } from '../models/task';
import { environment } from '../../../environments/environment';
import { ToastService } from './toast.service';
import { AuthService } from '../../auth/services/auth.service';


@Injectable({ providedIn: 'root' })
export class TasksService {

  private readonly appUrl: string = environment.apiUrl;

  constructor(
    private readonly authService:   AuthService,
    private readonly http:          HttpClient,
    private readonly toastService:  ToastService
  ) { }

  getTasks( params?: { completed?: boolean; id?: number; title?: string, page?: number; size?: number; sort?: string }): Observable<any> {
    return this.authService.getToken().pipe(
      switchMap( token => {
        if ( !token ) {
          this.toastService.showError( "User token not found. Request not sent" );
          return EMPTY;
        }

        return this.http.get<any>( this.appUrl, {
          params,
          headers: new HttpHeaders({ Authorization: `Bearer ${token}` })
        });
      }),
      catchError( error => {
        this.toastService.showError( `Error fetching tasks: ${error.message}` );
        return of({ content: [], totalPages: 0, totalElements: 0 });
      })
    );
  }

  createTask( title: string, description: string ): Observable<Task | null> {
    return this.authService.getToken().pipe(
      switchMap( token => {
        if ( !token ) {
          this.toastService.showError( "User token not found. Request not sent" );
          return of( null );
        }

        const task = { title, description, completed: false };

        return this.http.post<Task>( this.appUrl, task, {
          headers: new HttpHeaders({ Authorization: `Bearer ${token}` })
        });
      }),
      catchError( error => {
        this.toastService.showError( "Failed to create task" );
        return of(null);
      })
    );
  }

  deleteTask( id: number ): Observable<Task> {
    return this.authService.getToken().pipe(
      switchMap( token => {
        if ( !token ) {
          this.toastService.showError( "User token not found. Request not sent" );
          return EMPTY;
        }

        return this.http.delete<Task>( `${this.appUrl}/${id}`, {
          headers: new HttpHeaders({ Authorization: `Bearer ${token}` })
        });
      }),
      catchError( error => {
        this.toastService.showError( `Error deleting task: ${error.message}` );
        return throwError(() => new Error( 'Failed to delete task' ));
      })
    );
  }

  updateTaskCompletion( id: number ): Observable<Task> {
    return this.authService.getToken().pipe(
      switchMap( token => {
        if ( !token ) {
          this.toastService.showError( "User token not found. Request not sent" );
          return EMPTY;
        }

        return this.http.patch<Task>( `${this.appUrl}/${id}`, {}, {
          headers: new HttpHeaders({ Authorization: `Bearer ${token}` })
        });
      }),
      catchError( error => {
        this.toastService.showError( `Error updating task: ${error.message}` );
        return throwError(() => new Error( "Failed to update task" ));
      })
    );
  }

  updateTaskPriority( id: number, newPriority: number ): Observable<void> {
    return this.authService.getToken().pipe(
      switchMap( token => {
        if ( !token ) {
          this.toastService.showError( "User token not found. Request not sent" );
          return EMPTY;
        }

        const body = { priority: newPriority };

        return this.http.patch<void>( `${this.appUrl}/${id}`, body, {
          headers: new HttpHeaders({
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          }),
        });
      }),
      catchError( error => {
        this.toastService.showError( `Error updating task priority: ${error.message}` );
        return throwError(() => new Error( "Failed to update task priority" ));
      })
    );
  }

}
