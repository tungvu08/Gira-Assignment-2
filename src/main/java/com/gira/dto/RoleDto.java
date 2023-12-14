package com.gira.dto;

import com.gira.entity.Role;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private Role.Type type;
}
