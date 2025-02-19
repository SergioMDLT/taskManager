import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './tasks/pages/main-page/main-page.component';
import { AuthGuard } from './auth/services/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'tasks', pathMatch: 'full' },
  { path: 'tasks', component: MainPageComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'tasks' }
];

@NgModule({
  imports: [
    RouterModule.forRoot( routes )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
