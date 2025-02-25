import { inject, Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { AuthService as Auth0Service, User } from '@auth0/auth0-angular';
import { BehaviorSubject, catchError, from, map, Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly isBrowser: boolean;
  private readonly auth0?: Auth0Service;

  private readonly userSubject = new BehaviorSubject<User | null>( null );
  public user$ = this.userSubject.asObservable();

  private readonly isAuthenticatedSubject = new BehaviorSubject<boolean>( false );
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor( @Inject( PLATFORM_ID ) private readonly platformId: Object, private readonly http: HttpClient ) {
    this.isBrowser = isPlatformBrowser( platformId );

    if ( this.isBrowser ) {
      this.auth0 = inject( Auth0Service );

      this.auth0.isAuthenticated$.subscribe( isAuth => {
        this.isAuthenticatedSubject.next( isAuth );
      });

      this.auth0.user$.subscribe( user => {
        this.userSubject.next( user ?? null );
        if ( user ) this.registerUser( user );
      });

      this.auth0.getAccessTokenSilently().subscribe({
        next: ( token ) => {
          console.log( "Token reloaded" );
        },
        error: (err) => {
          console.warn( "Could not reload token automatically: ", err );
        }
      });

    } else {
      console.warn( 'Auth0Service is not executed on SSR' );
    }
  }

  private registerUser( user: User ): void {
    const userDTO = {
      auth0Id: user.sub,
      email: user.email
    };

    this.http.post( 'http://localhost:8080/users/register', userDTO )
      .subscribe({
        next: ( response ) => console.log( "User registered: ", response ),
        error: ( err ) => console.warn( "Error registering user: ", err )
      });
  }

  login(): void {
    if ( !this.isBrowser || !this.auth0 ) {
      console.warn( "Could not start session on SSR" );
      return;
    }

    this.auth0.loginWithRedirect({
      authorizationParams: { redirect_uri: 'http://localhost:4200/callback' },
      appState: { target: '/main' }
    });
  }

  logout(): void {
    if ( this.isBrowser && this.auth0 ) {
      this.auth0.logout({
        logoutParams: { returnTo: 'http://localhost:4200' }
      });
    }
  }

  getUser(): Observable<User | null> {
    return this.isBrowser && this.auth0 ? this.auth0.user$.pipe( map( user => user ?? null )) : of( null );
  }

  getToken(): Observable<string> {
    return this.isBrowser && this.auth0
      ? from( this.auth0.getAccessTokenSilently() ).pipe(
          catchError(error => {
            console.error("❌ Error reloading token:", error);
            return of('');
          })
        )
      : of('');
  }

}
