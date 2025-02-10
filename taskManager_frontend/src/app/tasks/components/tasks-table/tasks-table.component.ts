import { Component, Input } from '@angular/core';
import { Task } from '../../models/task';
import { TasksService } from '../../services/tasks.service';

@Component({
  selector: 'tasks-table',
  standalone: false,
  templateUrl: './tasks-table.component.html',
  styleUrl: './tasks-table.component.scss'
})
export class TasksTableComponent {

  @Input()
  public tasks: Task[] = [];

  constructor( private readonly tasksService: TasksService ) {}

  onUpdateTask( id: number ): void {
    this.tasksService.updateTask( id )
    .subscribe({
      next: ( updatedTask ) => {
        console.log(`Task with id ${id} updated successfully`);
        const index = this.tasks.findIndex(task => task.id === updatedTask.id);
        if (index !== -1) {
          this.tasks[index] = updatedTask;
        }
      },
      error: (err) => {
        console.error('Error creating task:', err);
      }
    });
  }

  onDeleteTask( id: number ): void {
    if( confirm( 'Delete forever?' )){
      this.tasksService.deleteTask( id )
      .subscribe({
        next: ( ) => {
          console.log(`Task with id ${id} deleted successfully`);
          this.tasks = this.tasks.filter((task) => task.id !== id);
        },
        error: (err) => {
          console.error('Error creating task:', err);
        }
      });
    }
  }

}
