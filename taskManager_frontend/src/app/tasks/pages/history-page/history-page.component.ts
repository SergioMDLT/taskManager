import { Component, OnInit } from '@angular/core';
import { Task } from '../../models/task';
import { TasksService } from '../../services/tasks.service';

@Component({
  selector: 'tasks-history-page',
  standalone: false,
  templateUrl: './history-page.component.html',
  styleUrl: './history-page.component.scss'
})
export class HistoryPageComponent implements OnInit {

  public tasks: Task[] = [];
  public isLoading: boolean = false;

  constructor( private readonly taskService: TasksService ) { }

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.isLoading = true;
    this.taskService.getTasks().subscribe({
      next: ( tasks ) => {
        setTimeout(() => {
          this.tasks = tasks;
          this.isLoading = false;
        }, 500);
      },
      error: ( err ) => {
        console.error( 'Error loading tasks:', err );
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
        this.tasks = tasks;
        this.isLoading = false;
      },
      error: ( err ) => {
        console.error( 'Error searching tasks:', err );
        this.isLoading = false;
      },
    });
  }

}
