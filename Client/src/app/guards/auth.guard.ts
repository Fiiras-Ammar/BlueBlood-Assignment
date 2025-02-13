// Angular core imports for route protection and dependency injection
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * AuthGuard protects routes from unauthorized access
 * Redirects to login page if user is not authenticated
 */
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Checks if route can be activated based on authentication status
   * @returns true if user is authenticated, false otherwise
   */
  canActivate(): boolean {
    if (this.authService.isAuthenticated()) {
      return true;
    }
    // Redirect to login page if not authenticated
    this.router.navigate(['/login']); 
    return false;
  }
}