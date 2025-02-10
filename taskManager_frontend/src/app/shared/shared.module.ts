import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SearchboxComponent } from './components/searchbox/searchbox.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';;
import { TaskFormComponent } from './components/task-form/task-form.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    SidebarComponent,
    SearchboxComponent,
    TaskFormComponent
  ],
  exports: [
    SidebarComponent,
    SearchboxComponent,
    TaskFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
  ],
  providers: [],
})
export class SharedModule { }
