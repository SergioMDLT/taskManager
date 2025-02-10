import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component } from '@angular/core';

@Component({
  selector: 'shared-task-form',
  standalone: false,
  templateUrl: './task-form.component.html',
  styleUrl: './task-form.component.scss'
})
export class TaskFormComponent {
  taskForm: FormGroup;

  constructor( private readonly fb: FormBuilder ) {
    this.taskForm = this.fb.group({
      title: [ '', Validators.required ],
      description: [ '' ]
    });
  }

  onSubmit() {
    if ( this.taskForm.valid ) {
      console.log( 'Nueva Tarea:', this.taskForm.value );
      this.taskForm.reset(); // Reinicia el formulario después de enviar
    }
  }
}
