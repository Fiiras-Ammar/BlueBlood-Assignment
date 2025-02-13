import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { TaskListComponent } from './components/task-list/task-list.component';
import { AuthGuard } from './guards/auth.guard';

/**
 * Application route configuration
 * Defines navigation paths and guards
 */
export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'tasks', component: TaskListComponent, canActivate: [AuthGuard] }, // Protected route
  { path: '', redirectTo: '/tasks', pathMatch: 'full' }, // Default route
  { path: '**', redirectTo: '/tasks' } // Wildcard route for undefined paths
];

@NgModule({
  imports: [RouterModule.forRoot(routes)], 
  exports: [RouterModule]
})
export class AppRoutingModule {}