import { NgModule } from '@angular/core';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { HistoryPageComponent } from './pages/history-page/history-page.component';
import { CreatePageComponent } from './pages/create-page/create-page.component';
import { TasksTableComponent } from './components/tasks-table/tasks-table.component';
import { SharedModule } from '../shared/shared.module';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { TasksRoutingModule } from './tasks-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { DragDropModule } from '@angular/cdk/drag-drop';


@NgModule({
  declarations: [
    CreatePageComponent,
    HistoryPageComponent,
    MainPageComponent,
    TasksTableComponent
  ],
  exports: [
    CreatePageComponent,
    HistoryPageComponent,
    MainPageComponent,
    TasksTableComponent
  ],
  imports: [
    CommonModule,
    DragDropModule,
    HttpClientModule,
    MatIconModule,
    SharedModule,
    TasksRoutingModule
  ],
  providers: [],
})
export class TasksModule { }
