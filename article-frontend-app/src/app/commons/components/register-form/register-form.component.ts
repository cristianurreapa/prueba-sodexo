import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterRequest } from '../../models/registerRequest.models';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AuthFormComponent } from '../auth-form/auth-form.component';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent {

  form: FormGroup;
  registerRequest: RegisterRequest = {};
  messageError: String | null = null;
  constructor(private fb: FormBuilder, private authService: AuthService, private dialog: MatDialog, public dialogRef: MatDialogRef<RegisterFormComponent>,) {

    this.form = this.fb.group(
      {
        email: ['', Validators.required],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        password: ['', Validators.required]
      }
    )

  }

  async register(registerRequest: RegisterRequest) {
    await this.authService.register(registerRequest)
    .then(()=> this.dialogRef.close())
    .catch((error) => {
       this.messageError = error.error;
    })
  }

  openLogin() {
    this.dialogRef.close();
    this.dialog.open(AuthFormComponent);
  }
}
