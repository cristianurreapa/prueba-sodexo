import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationRequest } from '../../models/authenticationRequest.models';
import { AuthenticationResponse } from '../../models/authenticationResponse.models';
import { lastValueFrom } from 'rxjs';
import { RegisterRequest } from '../../models/registerRequest.models';

@Injectable({
  providedIn: 'root'
})
export class AuthApiService {

  private baseUrl: string = "http://localhost:8080/api/v1/auth"

  constructor(private httpClient: HttpClient) {
  }

  async authenticationRequest(authenticationRequest: AuthenticationRequest): Promise<any> {
    const response = this.httpClient.post<AuthenticationResponse>(`${this.baseUrl}/authenticate`, authenticationRequest);
    
    return await lastValueFrom(response);
  }

  async registerRequest(registerRequest: RegisterRequest): Promise<AuthenticationResponse> {
    const response = this.httpClient.post<AuthenticationResponse>(`${this.baseUrl}/register`, registerRequest);
    return await lastValueFrom(response);
  }

  async refreshToken(): Promise<AuthenticationResponse> {
    const response = this.httpClient.post<AuthenticationResponse>(`${this.baseUrl}/refresh-token`, null);
    return await lastValueFrom(response);
  }

  async logout(): Promise<void> {
    const response = this.httpClient.post<void>(`${this.baseUrl}/logout`, null);
    return await lastValueFrom(response);
  }
}
