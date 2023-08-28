import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RegisterFormComponent } from '../register-form/register-form.component';
import { AuthFormComponent } from '../auth-form/auth-form.component';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent {

  isAuth: boolean = false;

  constructor(private dialog: MatDialog, private authService: AuthService) {

    this.authService.isAuthenticated$.subscribe((isAuth) => {
      this.isAuth = isAuth;
    })

    this.isAuth = this.authService.isAuthenticated();
  }

  openLogin() {
    this.dialog.open(AuthFormComponent)
  }

  openRegister() {
    this.dialog.open(RegisterFormComponent)
  }

  logout(){
    this.authService.logout()
  }
}
