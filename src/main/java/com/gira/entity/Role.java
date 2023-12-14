package com.gira.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.lang.reflect.Type;

@Data
@Entity
@Table(name="role")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Type type;

    public enum Type{
        ADMIN, USER
    }
}
