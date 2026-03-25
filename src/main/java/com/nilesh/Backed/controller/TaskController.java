package com.nilesh.Backed.controller;

import com.nilesh.Backed.model.Task;
import com.nilesh.Backed.model.TaskStatus;
import com.nilesh.Backed.model.User;
import com.nilesh.Backed.repository.TaskRepository;
import com.nilesh.Backed.repository.UserRepository;
import com.nilesh.Backed.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task) {

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Title is required");
        }

        if (task.getDeadline() == null) {
            return ResponseEntity.badRequest().body("Deadline is required");
        }

        Task savedTask = taskService.createTask(task);
        return ResponseEntity.ok(savedTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {
        try {
            Task updatedTask = taskService.updateTaskStatus(id, status);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<?> assignTask(@PathVariable Long taskId, @PathVariable Long userId) {
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Task not found"));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            task.setAssignedTo(user);
            Task updatedTask = taskRepository.save(task);

            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Task not found");
        }

        taskRepository.deleteById(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
}