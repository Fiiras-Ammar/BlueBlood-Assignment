// Required Angular and PrimeNG imports for form handling, routing, and UI components
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';

/**
 * LoginComponent handles user authentication through a login form.
 * Standalone component using PrimeNG UI components for the interface.
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [FormsModule, InputTextModule, ButtonModule, CardModule, MessageModule, MessagesModule]
})
export class LoginComponent {
  // Form fields for user credentials
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Handles form submission for user login
   * On success: Stores authentication token and redirects to tasks page
   * On failure: Displays error message to user
   */
  login() {
    const credentials = { username: this.username, password: this.password };

    this.authService.login(credentials).subscribe(
      (response: any) => {
        this.authService.setToken(response);
        this.router.navigate(['/tasks']);
      },
      (error: any) => {
        this.errorMessage = 'Invalid credentials';
      }
    );
  }
}