package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    // Inject TaskRepository to interact with the database
    @Autowired
    private TaskRepository taskRepository;

    // Create a new task with validation
    @Override
    public Task createTask(Task task) {
        // Validate task name is not empty
        if (task.getName() == null || task.getName().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be empty");
        }
        // Save and return the newly created task
        return taskRepository.save(task);
    }

    // Retrieve all tasks with filtering by completion status and sorting
    @Override
    public List<Task> getAllTasks(boolean completed, String sortBy) {
        // Return only completed tasks if 'completed' is true, otherwise return all tasks
        if (completed) {
            return taskRepository.findByCompleted(true);
        } else {
            return taskRepository.findAll();
        }
    }

    // Retrieve a task by its ID
    @Override
    public Task getTaskById(Long id) {
        // Throw an exception if task with the given ID does not exist
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
    }

    // Update an existing task
    @Override
    public Task updateTask(Long id, Task updatedTask) {
        // Fetch the existing task to update
        Task task = getTaskById(id);
        // Update task properties
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());
        // Save and return the updated task
        return taskRepository.save(task);
    }

    // Delete a task by its ID
    @Override
    public void deleteTask(Long id) {
        // Fetch the task to delete
        Task task = getTaskById(id);
        // Delete the task from the repository
        taskRepository.delete(task);
    }
}
