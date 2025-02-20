import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable, of, switchMap, throwError } from 'rxjs';
import { Task } from '../models/task';
import { environment } from '../../../environments/environment';
import { ToastService } from './toast.service';


@Injectable({ providedIn: 'root' })
export class TasksService {

  private readonly appUrl: string = environment.apiUrl;

  constructor(
    private readonly http: HttpClient,
    private readonly toastService: ToastService
  ) { }

  getTasks( params?: { completed?: boolean; id?: number; title?: string, page?: number; size?: number; sort?: string } ): Observable<any> {
    return this.http.get<any>( this.appUrl, { params } ).pipe(
      switchMap(isAuthenticated => {
        if (!isAuthenticated) {
          this.toastService.showError("Usuario no autenticado. No se enviará la solicitud");
          return EMPTY;
        }
        return this.http.get(this.appUrl);
      }),
      catchError(( error ) => {
        this.toastService.showError( `Error fetching tasks: ${ error }` );
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
        this.toastService.showError( `Error updating task priority: ${ error }` );
        throw error;
      })
    );
  }

}
