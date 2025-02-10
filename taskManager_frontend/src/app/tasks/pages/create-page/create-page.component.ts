import { Component } from '@angular/core';
import { TasksService } from '../../services/tasks.service';
import { Task } from '../../models/task';

@Component({
  selector: 'tasks-create-page',
  standalone: false,
  templateUrl: './create-page.component.html',
  styleUrl: './create-page.component.scss'
})
export class CreatePageComponent {

  public task: Task | null = null;

  constructor( private readonly tasksService: TasksService ) { }

  onFormSubmit( data: { title: string, description: string } ): void {
    this.postTask( data.title, data.description );
  }

  postTask( title: string, description: string ): void {
    this.tasksService.createTask( title, description )
      .subscribe({
        next: ( task ) => {
          this.task = task;
          console.log('Task created:', task);
        },
        error: (err) => {
          console.error('Error creating task:', err);
        }
    });
  }
}
