// Core Angular and common imports
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
// PrimeNG UI component imports
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { TaskFormComponent } from "../task-form/task-form.component";

/**
 * TaskListComponent handles the display and management of tasks
 * Provides CRUD operations through a REST API
 */
@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    TableModule,
    ButtonModule,
    DialogModule,
    ToastModule,
    TaskFormComponent
  ],
  providers: [MessageService]
})
export class TaskListComponent {
  // Array to store all tasks
  tasks: any[] = [];
  // Currently selected task for editing
  selectedTask: any = {};
  // Controls visibility of task edit/create dialog
  displayDialog: boolean = false;
  // Flag to distinguish between edit and create operations
  newTask: boolean = false;

  constructor(private http: HttpClient, private messageService: MessageService) {}

  /**
   * Lifecycle hook to load tasks when component initializes
   */
  ngOnInit(): void {
    this.loadTasks();
  }

  /**
   * Fetches all tasks from the backend API
   */
  loadTasks() {
    this.http.get<any[]>('http://localhost:8080/api/tasks').subscribe({
      next: (data) => {
        this.tasks = data;
      },
      error: (err) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to load tasks' });
        console.error('Error fetching tasks:', err);
      }
    });
  }

  /**
   * Deletes a task after user confirmation
   * @param task The task to be deleted
   */
  deleteTask(task: any) {
    if (confirm('Are you sure you want to delete this task?')) {
      this.http.delete(`http://localhost:8080/api/tasks/${task.id}`).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Task deleted' });
          this.loadTasks();
        },
        error: (err) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete task' });
          console.error('Error deleting task:', err);
        }
      });
    }
  }

  /**
   * Opens dialog to edit existing task
   * @param task The task to be edited
   */
  editTask(task: any) {
    this.newTask = false;
    this.selectedTask = { ...task };
    this.displayDialog = true;
  }

  /**
   * Opens dialog to create new task
   */
  addTask() {
    this.newTask = true;
    this.selectedTask = {};
    this.displayDialog = true;
  }

  /**
   * Handles saving of new or edited task
   * Determines whether to make POST or PUT request based on newTask flag
   */
  saveTask() {
    const apiCall = this.newTask
      ? this.http.post<any>('http://localhost:8080/api/tasks', this.selectedTask)
      : this.http.put<any>(`http://localhost:8080/api/tasks/${this.selectedTask.id}`, this.selectedTask);

    apiCall.subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: this.newTask ? 'Task created' : 'Task updated'
        });
        this.loadTasks();
        this.displayDialog = false;
      },
      error: (err) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to save task' });
        console.error('Error saving task:', err);
      }
    });
  }
}