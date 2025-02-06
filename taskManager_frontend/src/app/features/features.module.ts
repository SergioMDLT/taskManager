import { AllPageComponent } from '../presentation/pages/all-page/all-page.component';
import { CommonModule } from '@angular/common';
import { CompletedPageComponent } from '../presentation/pages/completed-page/completed-page.component';
import { CreatePageComponent } from '../presentation/pages/create-page/create-page.component';
import { NgModule } from '@angular/core';
import { PendingPageComponent } from '../presentation/pages/pending-page/pending-page.component';
import { RouterModule } from '@angular/router';
import { SearchBoxComponent } from './search-box/search-box.component';
import { SidebarComponent } from '../presentation/components/sidebar/sidebar.component';
import { SpinnerComponent } from '../presentation/components/spinner/spinner.component';
import { TaskListComponent } from './task-list/task-list.component';

@NgModule({
  declarations: [
    AllPageComponent,
    CompletedPageComponent,
    CreatePageComponent,
    PendingPageComponent,
    SearchBoxComponent,
    SidebarComponent,
    SpinnerComponent,
    TaskListComponent,
  ],
  exports: [
    AllPageComponent,
    CompletedPageComponent,
    CreatePageComponent,
    PendingPageComponent,
    SearchBoxComponent,
    SidebarComponent,
    SpinnerComponent,
    TaskListComponent,
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  providers: [

  ],
})
export class FeaturesModule { }
