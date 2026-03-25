package com.nilesh.Backed.service;

import com.nilesh.Backed.exception.GlobalExceptionHandler;
import com.nilesh.Backed.model.Role;
import com.nilesh.Backed.model.Task;
import com.nilesh.Backed.model.TaskStatus;
import com.nilesh.Backed.model.User;
import com.nilesh.Backed.repository.TaskRepository;
import com.nilesh.Backed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task createTask(Task task) {

        List<User> users = userRepository.findByRoleIn(
                List.of(Role.MANAGER, Role.EMPLOYEE)
        );

        if (users.isEmpty()) {
            throw new RuntimeException("No manager or employee available for task assignment");
        }

        User leastLoadedUser = null;
        long minTasks = Long.MAX_VALUE;

        for (User user : users) {
            long taskCount = taskRepository.countByAssignedToAndStatusNot(user, TaskStatus.COMPLETED);

            if (taskCount < minTasks) {
                minTasks = taskCount;
                leastLoadedUser = user;
            }
        }

        task.setAssignedTo(leastLoadedUser);

        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Task not found with id: " + taskId));

        task.setStatus(status);
        return taskRepository.save(task);
    }
}