package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(Role.RoleName role);
}
