import { Inject, Injectable, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { AuthService as Auth0Service, User } from '@auth0/auth0-angular';
import { from, map, Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly isBrowser: boolean = false;
  private readonly auth0?: Auth0Service;

  constructor( @Inject( PLATFORM_ID ) private readonly platformId: Object ) {
    this.isBrowser = isPlatformBrowser( platformId );

    if ( this.isBrowser ) {
      this.auth0 = inject( Auth0Service );
    }
  }

  login(): void {
    if ( this.isBrowser && this.auth0 ) {
      this.auth0.loginWithRedirect({
        appState: { target: '/tasks' }
      });
    }
  }

  logout(): void {
    if ( this.isBrowser && this.auth0 ) {
      this.auth0.logout({
        logoutParams: { returnTo: environment.auth0LogoutUrl }
      });
    }
  }

  getUser(): Observable<User | null> {
    return this.isBrowser && this.auth0 ? this.auth0.user$.pipe( map( user => user ?? null )) : of( null );
  }

  getToken(): Observable<string> {
    return this.isBrowser && this.auth0 ? from( this.auth0.getAccessTokenSilently() ) : of('');
  }

  isAuthenticated$: Observable<boolean> = this.isBrowser && this.auth0 ? this.auth0.isAuthenticated$ : of( false );

  /* private getBaseUrl(): string {
    return this.isBrowser ? `${window.location.protocol}//${window.location.host}` : '';
  } */

}
