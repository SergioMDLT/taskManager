import { Component } from '@angular/core';
import { TasksService } from '../../services/tasks.service';
import { Task } from '../../models/task';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'tasks-create-page',
  standalone: false,
  templateUrl: './create-page.component.html',
  styleUrl: './create-page.component.scss'
})
export class CreatePageComponent {

  public task: Task | null = null;

  constructor (
      private readonly taskService: TasksService,
      private readonly toastService: ToastService
     ) { }

  onFormSubmit( data: { title: string, description: string } ): void {
    this.postTask( data.title, data.description );
  }

  postTask( title: string, description: string ): void {
    this.taskService.createTask( title, description )
      .subscribe({
        next: ( task ) => {
          this.task = task;
          this.toastService.showSuccess( 'Task created successfully' );
        },
        error: (err) => {
          console.error('Error creating task:', err);
          this.toastService.showError( 'Error creating task' );
        }
    });
  }
}
