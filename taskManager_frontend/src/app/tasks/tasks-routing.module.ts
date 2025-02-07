import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreatePageComponent } from './pages/create-page/create-page.component';
import { HistoryPageComponent } from './pages/history-page/history-page.component';
import { MainPageComponent } from './pages/main-page/main-page.component';

const routes: Routes = [
  {
    path: 'main',
    component: MainPageComponent
  },
  {
    path: 'create-task',
    component: CreatePageComponent
  },
  {
    path: 'history',
    component: HistoryPageComponent
  },
  /* {
    path: 'by/:id',
    component:
  }, */
  {
    path: '**',
    redirectTo: 'main'
  },
]

@NgModule({
  exports: [
    RouterModule
  ],
  imports: [
    RouterModule.forChild( routes )
  ],
})
export class TasksRoutingModule { }
