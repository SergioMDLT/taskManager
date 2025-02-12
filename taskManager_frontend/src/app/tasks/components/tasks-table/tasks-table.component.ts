import { Component, Input } from '@angular/core';
import { Task } from '../../models/task';
import { TasksService } from '../../services/tasks.service';
import { ToastService } from '../../services/toast.service';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

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

  constructor (
      private readonly taskService: TasksService,
      private readonly toastService: ToastService
     ) { }

  onUpdateTask( id: number ): void {
    this.taskService.updateTask( id )
    .subscribe({
      next: ( updatedTask ) => {
        this.toastService.showSuccess( `Task with id ${id} updated successfully` );
        const index = this.tasks.findIndex(task => task.id === updatedTask.id);
        if (index !== -1) {
          this.tasks[index] = updatedTask;
        }
      },
      error: (err) => {
        console.error('Error creating task: ', err);
        this.toastService.showError( 'Error updating task' );
      }
    });
  }

  onDeleteTask( id: number ): void {
    if( confirm( 'Delete forever?' )){
      this.taskService.deleteTask( id )
      .subscribe({
        next: ( ) => {
          this.toastService.showSuccess( `Task with id ${id} deleted successfully` );
          this.tasks = this.tasks.filter((task) => task.id !== id);
        },
        error: (err) => {
          console.error('Error deleting task: ', err);
          this.toastService.showError( 'Error deleting task' );
        }
      });
    }
  }

  onDrop( event: CdkDragDrop<Task[]> ): void {
    moveItemInArray( this.tasks, event.previousIndex, event.currentIndex );

    const updatedTask = this.tasks[ event.currentIndex ];
    const newPriority = event.currentIndex + 1;

    this.taskService.updateTaskPriority( updatedTask.id, newPriority )
    .subscribe({
      next: () => {
        console.log( `Priority updated for task ${ updatedTask.id } to ${ newPriority }`);
      },
      error: (err) => {
        console.error( 'Failed to update priority:', err );
        moveItemInArray( this.tasks, event.currentIndex, event.previousIndex );
      },
    });
  }

}
