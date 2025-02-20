import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivate, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { ToastService } from '../../tasks/services/toast.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

  private readonly isBrowser: boolean;

  constructor(
    private readonly toastService: ToastService,
    private readonly auth: AuthService,
    private readonly router: Router,
    @Inject( PLATFORM_ID )
    private readonly platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser( platformId );
  }

  canActivate(): Observable<boolean> {
    if (!this.isBrowser) {
      return of(false); // No redirigir en SSR
    }

    return this.auth.isAuthenticated$.pipe(
      tap(isAuthenticated => {
        if (!isAuthenticated) {
          console.warn("Usuario NO autenticado, intentando recuperar sesión...");

          // Intentamos recuperar la sesión primero
          this.auth.getToken().subscribe({
            next: (token) => {
              if (!token) {
                console.warn("No se pudo recuperar sesión, redirigiendo a login...");
                this.auth.login();
              }
            },
            error: (err) => {
              console.error("Error recuperando token:", err);
              this.auth.login();
            }
          });
        }
      }),
      map(isAuthenticated => isAuthenticated),
      catchError(error => {
        console.error("Error verificando autenticación:", error);
        return of(false);
      })
    );
  }
}
