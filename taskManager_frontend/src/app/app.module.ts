import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { SharedModule } from './shared/shared.module';
import { TasksModule } from './tasks/tasks.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { authConfig } from './auth.config';
import { AuthInterceptor } from './auth/interceptors/auth.interceptor';
import { authClientProvider } from './auth/auth.provider';
import { AuthModule } from '@auth0/auth0-angular';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    AppRoutingModule,
    AuthModule.forRoot( authConfig ),
    BrowserModule,
    DragDropModule,
    HttpClientModule,
    MatSnackBarModule,
    SharedModule,
    TasksModule,
  ],
  providers: [
    provideClientHydration(withEventReplay()),
    provideAnimationsAsync(),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    authClientProvider,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
