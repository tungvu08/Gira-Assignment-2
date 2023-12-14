package com.gira.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CommentCreateForm {
    @NotBlank(message = "The comment name must not be blank")
    @Length(message = "The comment name has a maximum  of 255 char")
    private String name;

    @Email(message = "The comment email must contain @ char")
    @NotBlank(message = "The comment email must not be blank")
    @Length(message = "The comment email has a maximum  of 255 char")
    private String email;

    @NotBlank(message = "The comment body must not be blank")
    @Length(message = "The comment body has a maximum  of 255 char")
    private String body;
}
