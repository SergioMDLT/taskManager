import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SearchboxComponent } from './components/searchbox/searchbox.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';


@NgModule({
  declarations: [
    SidebarComponent,
    SearchboxComponent
  ],
  exports: [
    SidebarComponent,
    SearchboxComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  providers: [],
})
export class SharedModule { }
