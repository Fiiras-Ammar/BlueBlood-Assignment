/**
 * Root component of the application
 * Provides the main router outlet for navigation
 */
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common'; 

@Component({
  selector: 'app-root',
  template: `
    <router-outlet></router-outlet> 
  `,
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [RouterModule, CommonModule]
})
export class AppComponent {
  title = 'Task Management';
}