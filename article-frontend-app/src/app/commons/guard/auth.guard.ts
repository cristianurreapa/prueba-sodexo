import { Injectable } from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { AuthFormComponent } from '../components/auth-form/auth-form.component';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {

  constructor(public authService: AuthService, public router: Router, private dialog: MatDialog) { }

  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/'])
      this.dialog.open(AuthFormComponent)
    }
    return true;
  }

}
