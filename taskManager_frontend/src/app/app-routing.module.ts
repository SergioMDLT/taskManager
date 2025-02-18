import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './tasks/pages/main-page/main-page.component';
import { AuthGuard } from './services/auth.guard';

const routes: Routes = [
  { path: 'tasks', component: MainPageComponent, canActivate: [ AuthGuard ] }
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
