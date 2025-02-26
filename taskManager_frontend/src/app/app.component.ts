import { Component } from '@angular/core';
import { AuthService } from './auth/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.scss'
})
export class AppComponent {

  title = 'Task Manager';

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe( isAuthenticated => {
      if ( isAuthenticated ) {
        this.router.navigate(['/main']);
      }
    });
  }

}
