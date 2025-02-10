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

  constructor( private readonly taskService: TasksService ) { }

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.taskService.getTasks()
      .subscribe( tasks => this.tasks = tasks );
  }

  onSearch( term: string ): void {
    if ( term.trim().length === 0 ){
      this.loadTasks();
      return;
    }

    this.taskService.getTasks( { title: term })
      .subscribe({
        next: (tasks) => {
          this.tasks = tasks;
        },
        error: (err) => console.error('Error searching tasks:', err),
      });
  }

}
