import { CommonModule } from '@angular/common';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SearchboxComponent } from './components/searchbox/searchbox.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';;
import { SpinnerComponent } from './components/spinner/spinner.component';
import { TaskFormComponent } from './components/task-form/task-form.component';


@NgModule({
  declarations: [
    SearchboxComponent,
    SidebarComponent,
    SpinnerComponent,
    TaskFormComponent,
  ],
  exports: [
    MatSnackBarModule,
    SearchboxComponent,
    SidebarComponent,
    SpinnerComponent,
    TaskFormComponent
  ],
  imports: [
    CommonModule,
    MatSnackBarModule,
    ReactiveFormsModule,
    RouterModule,
  ],
  providers: [],
})
export class SharedModule { }
