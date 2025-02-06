import { NgModule } from '@angular/core';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { HistoryPageComponent } from './pages/history-page/history-page.component';
import { CreatePageComponent } from './pages/create-page/create-page.component';
import { TasksTableComponent } from './components/tasks-table/tasks-table.component';
import { SharedModule } from '../shared/shared.module';
import { CommonModule } from '@angular/common';
import { TasksRoutingModule } from './tasks-routing.module';


@NgModule({
  declarations: [
    MainPageComponent,
    HistoryPageComponent,
    CreatePageComponent,
    TasksTableComponent
  ],
  exports: [],
  imports: [
    CommonModule,
    SharedModule,
    TasksRoutingModule
  ],
  providers: [],
})
export class TasksModule { }
