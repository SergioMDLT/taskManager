import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Task } from '../../models/task';
import { TasksService } from '../../services/tasks.service';
import { ToastService } from '../../services/toast.service';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'tasks-table',
  standalone: false,
  templateUrl: './tasks-table.component.html',
  styleUrl: './tasks-table.component.scss'
})
export class TasksTableComponent {

  @Input()
  public tasks: Task[] = [];

  @Input()
  showPriority: boolean = false;

  @Output() taskCompleted: EventEmitter<Task> = new EventEmitter<Task>();

  constructor (
    private readonly taskService:   TasksService,
    private readonly toastService: ToastService
  ) { }

  onUpdateTask( id: number ): void {
    this.taskService.updateTaskCompletion( id )
    .subscribe({
      next: ( updatedTask ) => {
        this.toastService.showSuccess( `Task with id ${id} updated successfully` );
        const index = this.tasks.findIndex( task => task.id === updatedTask.id );

        if ( index !== -1 ) {
          this.tasks[index] = updatedTask;
        }

        if ( updatedTask.completed ) {
          this.adjustPrioritiesAfterCompletion( updatedTask );
          this.taskCompleted.emit( updatedTask );
        }
      },
      error: ( err ) => {
        console.error( "Error creating task: ", err );
        this.toastService.showError( "Error updating task" );
      }
    });
  }

  onDeleteTask( id: number ): void {
    if( confirm( "Delete forever?" )){
      this.taskService.deleteTask( id )
      .subscribe({
        next: ( ) => {
          this.toastService.showSuccess( `Task with id ${id} deleted successfully` );
          this.tasks = this.tasks.filter(( task ) => task.id !== id );
        },
        error: ( err ) => {
          console.error( "Error deleting task: ", err);
          this.toastService.showError( "Error deleting task" );
        }
      });
    }
  }

  private adjustPrioritiesAfterCompletion( task: Task ): void {
    const removedPriority = task.priority;

    this.tasks.forEach( t => {
      if ( t.priority !== null && removedPriority !== null && t.priority > removedPriority ) {
        t.priority -= 1;
      }
    });

    this.tasks = this.tasks.filter( t => t.id !== task.id );

    this.taskCompleted.emit( task );
  }

  onDrop( event: CdkDragDrop<Task[]> ): void {
    if ( event.previousIndex === event.currentIndex ) return;
    moveItemInArray( this.tasks, event.previousIndex, event.currentIndex );

    const updatedTasks = this.tasks.map(( task, index ) => ({
      ...task,
      priority: index + 1,
    }));

    const updateRequests = updatedTasks.map(( task ) =>
      this.taskService.updateTaskPriority( task.id, task.priority )
    );

    forkJoin( updateRequests ).subscribe({
      next: () => {
        console.log( "All priorities updated successfully" );
        this.tasks = updatedTasks;
      },
      error: ( err ) => {
        console.error( "Failed to update priorities: ", err );
        moveItemInArray( this.tasks, event.currentIndex, event.previousIndex );
        this.tasks = this.tasks.map(( task, index ) => ({
          ...task,
          priority: index + 1,
        }));
      },
    });
  }

}
