package com.nilesh.Backed.repository;

import com.nilesh.Backed.model.Task;
import com.nilesh.Backed.model.TaskStatus;
import com.nilesh.Backed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo(User user);

    long countByAssignedToAndStatusNot(User user, TaskStatus status);

    @Query("""
        SELECT t.assignedTo
        FROM Task t
        GROUP BY t.assignedTo
        ORDER BY COUNT(t) ASC
    """)
    List<User> findUsersByTaskCount();
}