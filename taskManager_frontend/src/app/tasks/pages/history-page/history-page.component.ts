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

  constructor( private readonly tasksService: TasksService ) { }

  ngOnInit(): void {
    this.tasksService.getHistory()
    .subscribe( tasks => this.tasks = tasks );
  }

}
