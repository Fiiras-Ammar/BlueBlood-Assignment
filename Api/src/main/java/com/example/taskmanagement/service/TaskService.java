package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Task;
import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    List<Task> getAllTasks(boolean completed, String sortBy);
    Task getTaskById(Long id);
    Task updateTask(Long id, Task updatedTask);
    void deleteTask(Long id);
}