package com.gira.service;

import com.gira.dto.PostDto;
import com.gira.form.PostCreateForm;
import com.gira.form.PostFilterForm;
import com.gira.form.PostUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDto> findAll(PostFilterForm form, Pageable pageable);
    PostDto create(PostCreateForm form);
    PostDto findById(Long id);

    PostDto update(Long id, PostUpdateForm form);
    void deleteById(Long id);
}
