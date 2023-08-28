import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { AuthenticationRequest } from '../models/authenticationRequest.models';
import { RegisterRequest } from '../models/registerRequest.models';
import { AuthApiService } from './api/auth-api.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private authApi: AuthApiService, private router: Router) {
  }

  async authentication(authenticationRequest: AuthenticationRequest): Promise<any> {

    return this.authApi.authenticationRequest(authenticationRequest).then((ok) => {
      this.storeAccessToken(ok.accessToken);
      this.storeRefreshToken(ok.refreshToken);
      this.isAuthenticatedSubject.next(true);
    }).catch((error) => {
      return Promise.reject(error)
    }
    );
  }

  async register(registerRequest: RegisterRequest) {
    return this.authApi.registerRequest(registerRequest).then((ok) => {
      this.storeAccessToken(ok.accessToken);
      this.storeRefreshToken(ok.refreshToken);
      this.isAuthenticatedSubject.next(true);
    }).catch((error) => {
      return Promise.reject(error)
    }
    );
  }

  refreshToken() {
    this.authApi.refreshToken().then((ok) => {
      this.isAuthenticatedSubject.next(true);
    }).catch((error) => {
      console.error(error)
    }
    );
  }

  logout(): void {
    this.authApi.logout();
    this.removeAccessToken();
    this.removeRefreshToken();
    this.isAuthenticatedSubject.next(false);
    this.router.navigate(['/']);
  }

  public getAccessToken(): string {
    return localStorage.getItem('access_token')!;
  }

  private storeAccessToken(token: string): void {
    localStorage.setItem('access_token', token);
  }

  private removeAccessToken(): void {
    localStorage.removeItem('access_token');
  }

  private getRefreshToken(): string | null {
    return localStorage.getItem('refresh_token');
  }

  private storeRefreshToken(token: string): void {
    localStorage.setItem('refresh_token', token);
  }

  private removeRefreshToken(): void {
    localStorage.removeItem('refresh_token');
  }

  isAuthenticated(): boolean {
    let payload = this.obtenerDatosToken(this.getAccessToken());
    if (payload != null && payload.sub && payload.sub.length > 0) {
      return true;
    }
    return false;
  }

  private obtenerDatosToken(accessToken: string | null): any {
    if (accessToken != null) {
      return JSON.parse(atob(accessToken.split(".")[1]));
    }
    return null;
  }

}
