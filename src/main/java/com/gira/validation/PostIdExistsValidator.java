package com.gira.validation;

import com.gira.repository.PostRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PostIdExistsValidator implements ConstraintValidator<PostIdExists, Long> {
    private final PostRepository postRepository;

    @Autowired
    public PostIdExistsValidator(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
            return postRepository.existsById(id);
    }
}
