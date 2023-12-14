package com.gira.controller;

import com.gira.dto.CommentDto;
import com.gira.form.CommentCreateForm;
import com.gira.form.CommentUpdateForm;
import com.gira.service.CommentService;
import com.gira.validation.PostIdExists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class CommentController {
private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/v1/post/{postId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable ("postId") @PostIdExists Long postId, @RequestBody @Valid CommentCreateForm form){
        return commentService.create(postId, form);
    }

    @GetMapping("/api/v1/comments")
    public Page<CommentDto> findAll(Pageable pageable){
        return commentService.findAll(pageable);
    }

    @GetMapping("/api/v1/post/{postId}/comments")
    public Page<CommentDto> findByPostId(@PathVariable("postId") Long postId, Pageable pageable){
        return commentService.findByPostId(postId, pageable);
    }

    @GetMapping("/api/v1/comment/{id}")
    public CommentDto findById(@PathVariable("id") Long id){
        return commentService.findById(id);
    }

    @PutMapping("/api/v1/comment/{commentId}")
    public CommentDto update(@PathVariable("commentId") Long commentId, @RequestBody @Valid CommentUpdateForm form){
        return commentService.update(commentId, form);
    }

    @DeleteMapping("/api/v1/comment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id ){
        commentService.deleteById(id);
    }
}
