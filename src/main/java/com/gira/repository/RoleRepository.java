package com.gira.repository;

import com.gira.dto.RoleDto;
import com.gira.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {
        Role findByType(Role.Type type);
}
