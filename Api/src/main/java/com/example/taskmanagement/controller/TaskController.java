package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // Injecting the TaskService to handle business logic
    @Autowired
    private TaskService taskService;

    // Endpoint to create a new task
    // It accepts a Task object in the request body and returns the created task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    // Endpoint to get all tasks
    // Allows filtering by completion status (completed) and sorting by a specified field (sortBy)
    // Defaults to non-completed tasks and sorting by 'createdAt'
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false, defaultValue = "false") boolean completed,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy) {
        return ResponseEntity.ok(taskService.getAllTasks(completed, sortBy));
    }

    // Endpoint to get a task by its ID
    // Returns the task object with the specified ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // Endpoint to update an existing task
    // Accepts the task ID in the URL and the updated task object in the request body
    // Returns the updated task object
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return ResponseEntity.ok(taskService.updateTask(id, updatedTask));
    }

    // Endpoint to delete a task by its ID
    // Returns a no-content response after successfully deleting the task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
