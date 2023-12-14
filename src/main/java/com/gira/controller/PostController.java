package com.gira.controller;

import com.gira.dto.PostDto;
import com.gira.form.PostCreateForm;
import com.gira.form.PostFilterForm;
import com.gira.form.PostUpdateForm;
import com.gira.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/v1/posts")
    public Page<PostDto> findAll(PostFilterForm form, Pageable pageable){
        return postService.findAll(form, pageable);
    }

    @PostMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto create(@RequestBody @Valid PostCreateForm form) {

        return postService.create(form);
    }

    @GetMapping("/api/v1/post/{id}")
    public PostDto findById(@PathVariable("id") Long id){

        return postService.findById(id);
    }

    @PutMapping("/api/v1/post/{id}")
    public PostDto update(@PathVariable("id") Long id, @RequestBody @Valid PostUpdateForm form){
        return postService.update(id, form);
    }
    @DeleteMapping("/api/v1/post/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        postService.deleteById(id);
    }
}
