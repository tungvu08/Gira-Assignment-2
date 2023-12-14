package com.gira.form;

import lombok.Data;

@Data
public class UserCreateForm {
    private String name;
    private String username;
    private  String email;
    private String password;
}
