package com.nilesh.Backed.repository;

import com.nilesh.Backed.model.Role;
import com.nilesh.Backed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRoleIn(List<Role> roles);
}