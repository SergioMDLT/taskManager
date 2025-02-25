import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor( private readonly authService: AuthService ) {}

  intercept( req: HttpRequest<any>, next: HttpHandler ): Observable<HttpEvent<any>> {
    return this.authService.getToken().pipe(
      switchMap( token => {
        if ( !token ) {
          return next.handle( req );
        }

        const clonedReq = req.clone({
          setHeaders: {
            Authorization: `Bearer ${ token }`
          }
        });

        return next.handle( clonedReq );

      })
    );
  }

}
