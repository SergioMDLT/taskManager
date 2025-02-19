import { Component } from '@angular/core';
import { AuthService } from '../../../auth/services/auth.service';


@Component({
  selector: 'shared-sidebar',
  standalone: false,
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  constructor( public auth: AuthService ) {}

}
