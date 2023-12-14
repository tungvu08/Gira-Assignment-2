package com.gira.service;

import com.gira.dto.CommentDto;
import com.gira.form.CommentCreateForm;
import com.gira.form.CommentUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDto create(Long postId, CommentCreateForm form);
    Page<CommentDto> findAll(Pageable pageable);

    Page<CommentDto> findByPostId(Long postId, Pageable pageable);
    CommentDto findById(Long id);

    CommentDto update(Long CommentId, CommentUpdateForm form);

    void deleteById(Long id);
}
