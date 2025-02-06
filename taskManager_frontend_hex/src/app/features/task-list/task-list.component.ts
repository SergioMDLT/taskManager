import { Component, Input } from '@angular/core';
import { Task } from '../../core/models/task.model';

@Component({
  selector: 'features-task-list',
  standalone: false,
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.scss'
})
export class TaskListComponent {

  @Input()
  public tasks: Task[] = [];

}
