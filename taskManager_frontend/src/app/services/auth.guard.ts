import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

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
    return this.auth.isAuthenticated$().pipe(
      tap( isAuthenticated => {
        if ( !isAuthenticated && this.isBrowser ) {
          this.auth.login();
        }
      })
    );
  }
}
