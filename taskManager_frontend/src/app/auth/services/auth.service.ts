import { inject, Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { AuthService as Auth0Service, User } from '@auth0/auth0-angular';
import { BehaviorSubject, from, map, Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly isBrowser: boolean;
  private readonly auth0?: Auth0Service;

  private readonly userSubject = new BehaviorSubject<User | null>(null);
  public user$ = this.userSubject.asObservable();

  private readonly isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    this.isBrowser = isPlatformBrowser(platformId);

    if (this.isBrowser) {
      // Inyectar Auth0Service solo en el navegador
      this.auth0 = inject(Auth0Service);

      this.auth0.isAuthenticated$.subscribe(isAuth => {
        this.isAuthenticatedSubject.next(isAuth);
      });

      this.auth0.user$.subscribe(user => {
        this.userSubject.next(user ?? null);
      });

      this.auth0.getAccessTokenSilently().subscribe({
        next: (token) => {
          console.log("Token recuperado:", token);
        },
        error: (err) => {
          console.warn("No se pudo recuperar token automáticamente:", err);
        }
      });

    } else {
      console.warn('Auth0Service no se ejecuta en SSR.');
    }
  }

  login(): void {
    if (!this.isBrowser || !this.auth0) {
      console.warn("No se puede iniciar sesión en SSR");
      return;
    }

    this.auth0.loginWithRedirect({
      authorizationParams: { redirect_uri: 'http://localhost:4200/callback' },
      appState: { target: '/tasks' }
    });
  }

  logout(): void {
    if (this.isBrowser && this.auth0) {
      this.auth0.logout({
        logoutParams: { returnTo: 'http://localhost:4200' }
      });
    }
  }

  getUser(): Observable<User | null> {
    return this.isBrowser && this.auth0 ? this.auth0.user$.pipe(map(user => user ?? null)) : of(null);
  }

  getToken(): Observable<string> {
    return this.isBrowser && this.auth0 ? from(this.auth0.getAccessTokenSilently()) : of('');
  }
}
