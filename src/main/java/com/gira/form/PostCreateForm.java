package com.gira.form;

import ch.qos.logback.core.joran.conditional.ThenAction;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PostCreateForm {
    @NotBlank(message = "{post.title.NotBlank.message}")
    @Length(message = "The post title has a maximum  of 255 char")
    private String title;

    @NotBlank(message = "The post description must not be blank")
    @Length(message = "The post description has a maximum  of 255 char")
    private String description;

    @NotBlank(message = "The post content must not be blank")
    @Length(message = "The post content has a maximum  of 255 char")
    private String content;
}
