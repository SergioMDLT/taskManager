import { Component } from '@angular/core';

@Component({
  selector: 'app-create-task',
  imports: [],
  templateUrl: './create-task.component.html',
  styleUrl: './create-task.component.scss'
})
export class CreateTaskComponent {
  title = '';
  description = '';

  constructor(private taskService: TaskService) {}

  createTask(): void {
    if (!this.title.trim()) return;
    this.taskService.createTask({ title: this.title, description: this.description, completed: false });
    this.title = '';
    this.description = '';
  }
}
