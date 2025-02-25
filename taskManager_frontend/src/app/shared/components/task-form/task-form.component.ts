import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'shared-task-form',
  standalone: false,
  templateUrl: './task-form.component.html',
  styleUrl: './task-form.component.scss'
})
export class TaskFormComponent {

  taskForm: FormGroup;

  @Output() formSubmit = new EventEmitter<{ title: string; description: string }>();

  constructor( private readonly formBuilder: FormBuilder ) {
    this.taskForm = this.formBuilder.group({
      title: [ '', Validators.required ],
      description: [ '' ]
    });
  }

  onSubmit() {
    if ( this.taskForm.valid ) {
      this.formSubmit.emit( this.taskForm.value );
      this.taskForm.reset();
    }
  }

}
