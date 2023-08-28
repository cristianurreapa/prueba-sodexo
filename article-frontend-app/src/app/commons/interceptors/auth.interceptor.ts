import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../components/error-dialog/error-dialog.component';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {


  constructor(private dialog: MatDialog) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
      })
    };

    if (request.url.includes('auth') || request.url.includes('spaceflightnewsapi')) {
      return next.handle(request);
    }

    if (localStorage.getItem('access_token') != null) {
      request = request.clone(httpOptions);
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {

        if (error.status == 403) {
          this.dialog.open(ErrorDialogComponent, { data: "Error al validar las credenciales del usuario. \n"+  "La solicitud " + request.method + " " + request.url + " obtuvo un error HTTP " + error.status})
          return throwError(() => error);
        }

        this.dialog.open(ErrorDialogComponent, { data: "La solicitud " + request.method + " " + request.url + " obtuvo un error HTTP " + error.status })
        return throwError(() => error);
      }
      ))
  }
}
