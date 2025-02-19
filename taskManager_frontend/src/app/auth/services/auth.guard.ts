import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  private readonly isBrowser: boolean;

  constructor(
    private readonly auth: AuthService,
    private readonly router: Router,
    @Inject( PLATFORM_ID ) private readonly platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser( platformId );
  }

  canActivate(): Observable<boolean> {
    return this.auth.isAuthenticated$.pipe(
      map( isAuthenticated => {
        if ( !isAuthenticated && this.isBrowser ) {
          console.log( "Usuario no autenticado. Redirigiendo a Auth0..." );
          this.auth.login();
          return false;
        }
        return true;
      })
    );
  }

}
