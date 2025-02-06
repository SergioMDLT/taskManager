import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PendingPageComponent } from '../presentation/pages/pending-page/pending-page.component';
import { CreatePageComponent } from '../presentation/pages/create-page/create-page.component';
import { CompletedPageComponent } from '../presentation/pages/completed-page/completed-page.component';
import { AllPageComponent } from '../presentation/pages/all-page/all-page.component';

const routes: Routes = [
  {
      path: 'pending',
      component: PendingPageComponent
    },
    {
      path: 'create',
      component: CreatePageComponent
    },
    {
      path: 'completed',
      component: CompletedPageComponent
    },
    {
      path: 'all',
      component: AllPageComponent
    }
]

@NgModule({
  declarations: [],
  exports: [],
  imports: [
    RouterModule.forChild( routes )
  ],
  providers: [],
})
export class FeaturesRoutingModule { }
