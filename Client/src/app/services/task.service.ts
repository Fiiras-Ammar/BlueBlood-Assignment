// Angular core and HTTP client imports
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

/**
* TaskService handles CRUD operations for tasks
* Communicates with backend API endpoints
*/
@Injectable({
 providedIn: 'root'
})
export class TaskService {
 // API URL from environment configuration
 private apiUrl = environment.apiUrl; 

 constructor(private http: HttpClient) {}

 /**
  * Retrieves all tasks from the API
  * @returns Observable of task array
  */
 getTasks(): Observable<any[]> {
   return this.http.get<any[]>(`${this.apiUrl}/api/tasks`);
 }

 /**
  * Creates a new task
  * @param task Task data to be created
  * @returns Observable of created task
  */
 createTask(task: any): Observable<any> {
   return this.http.post(`${this.apiUrl}/api/tasks`, task);
 }

 /**
  * Updates an existing task
  * @param id ID of task to update
  * @param task Updated task data
  * @returns Observable of updated task
  */
 updateTask(id: number, task: any): Observable<any> {
   return this.http.put(`${this.apiUrl}/api/tasks/${id}`, task);
 }

 /**
  * Deletes a task
  * @param id ID of task to delete
  * @returns Observable of delete operation result
  */
 deleteTask(id: number): Observable<any> {
   return this.http.delete(`${this.apiUrl}/api/tasks/${id}`);
 }
}