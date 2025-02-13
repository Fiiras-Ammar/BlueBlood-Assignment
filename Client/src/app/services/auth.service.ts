// Angular core and HTTP client imports
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

/**
* AuthService handles authentication operations and token management
* Uses local storage for token persistence
*/
@Injectable({
 providedIn: 'root'
})
export class AuthService {
 // API URL from environment configuration
 private apiUrl = environment.apiUrl; 

 constructor(private http: HttpClient) {}

 /**
  * Authenticates user with provided credentials
  * @param credentials Object containing username and password
  * @returns Observable of the authentication response
  */
 login(credentials: { username: string, password: string }): Observable<any> {
   return this.http.post(`${this.apiUrl}/api/auth/login`, credentials);
 }

 /**
  * Stores authentication token in local storage
  * @param token JWT token from successful authentication
  */
 setToken(token: string) {
   localStorage.setItem('token', token);
 }

 /**
  * Retrieves stored authentication token
  * @returns The stored token or null if not found
  */
 getToken(): string | null {
   return localStorage.getItem('token');
 }

 /**
  * Removes authentication token to log user out
  */
 logout() {
   localStorage.removeItem('token');
 }

 /**
  * Checks if user is currently authenticated
  * @returns boolean indicating whether valid token exists
  */
 isAuthenticated(): boolean {
   return !!this.getToken();
 }
}