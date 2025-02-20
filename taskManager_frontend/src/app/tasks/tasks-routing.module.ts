import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreatePageComponent } from './pages/create-page/create-page.component';
import { HistoryPageComponent } from './pages/history-page/history-page.component';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { AuthGuard } from '../auth/services/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'main', pathMatch: 'full' },
  { path: 'main', component: MainPageComponent, canActivate: [AuthGuard] },
  { path: 'create-task', component: CreatePageComponent, canActivate: [AuthGuard] },
  { path: 'history', component: HistoryPageComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'main' }
];

@NgModule({
  exports: [
    RouterModule
  ],
  imports: [
    RouterModule.forChild( routes )
  ],
})
export class TasksRoutingModule { }
