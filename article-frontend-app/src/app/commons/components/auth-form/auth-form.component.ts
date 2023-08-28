import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationRequest } from '../../models/authenticationRequest.models';
import { Router } from '@angular/router';
import { DialogInfo } from '../../models/dialogInfo.models';
import Swal from 'sweetalert2';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { RegisterFormComponent } from '../register-form/register-form.component';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-auth-form',
  templateUrl: './auth-form.component.html',
  styleUrls: ['./auth-form.component.scss']
})
export class AuthFormComponent implements OnInit {

  form: FormGroup;
  showDialog: boolean = false;
  authenticationRequest: AuthenticationRequest = {};
  resultAuth: DialogInfo | undefined;
  messageError: String | null = null;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private dialog: MatDialog, public dialogRef: MatDialogRef<AuthFormComponent>,) {
    this.form = this.fb.group(
      {
        email: ['', Validators.required],
        password: ['', Validators.required]
      }
    )
  }

  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe((isAuth) => {
      if (isAuth || this.authService.isAuthenticated()) {
        this.router.navigate(['/favorite'])
      }
    })

  }

  async login(authenticationRequest: AuthenticationRequest) {
    await this.authService.authentication(authenticationRequest)
    .then(() => {
      this.dialogRef.close()
    }).catch((error) => {
      this.messageError = error.error;
    })
  }

  openRegister() {
    this.dialogRef.close();
    this.dialog.open(RegisterFormComponent)
  }

}
