import { Component, Input } from '@angular/core';
import { Task } from '../../models/task';

@Component({
  selector: 'tasks-table',
  standalone: false,
  templateUrl: './tasks-table.component.html',
  styleUrl: './tasks-table.component.scss'
})
export class TasksTableComponent {

  @Input()
  public tasks: Task[] = [];

}
