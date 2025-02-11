import { Component, OnInit } from '@angular/core';
import { Task } from '../../models/task';
import { TasksService } from '../../services/tasks.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'tasks-main-page',
  standalone: false,
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent implements OnInit {

  public tasks: Task[] = [];
  public isLoading: boolean = false;

  constructor (
    private readonly taskService: TasksService,
    private readonly toastService: ToastService
   ) { }

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.isLoading = true;
    this.taskService.getTasks({ completed: false }).subscribe({
      next: ( tasks ) => {
        setTimeout(() => {
          this.tasks = tasks;
          this.isLoading = false;
        }, 500);
      },
      error: ( err ) => {
        console.error( 'Error loading tasks:', err );
        this.toastService.showError( 'Error loading tasks' );
        this.isLoading = false;
      },
    });
  }

  onSearch( term: string ): void {
    if ( term.trim().length === 0 ){
      this.loadTasks();
      return;
    }
    this.isLoading = true;
    this.taskService.getTasks({ title: term }).subscribe({
      next: ( tasks ) => {
        setTimeout(() => {
          this.tasks = tasks;
          this.isLoading = false;
        }, 500);
      },
      error: ( err ) => {
        console.error( 'Error searching tasks:', err );
        this.toastService.showError( 'Error searching tasks' );
        this.isLoading = false;
      },
    });
  }

}
