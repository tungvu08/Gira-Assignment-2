package com.gira.service;

import com.gira.dto.PostDto;
import com.gira.entity.Post;
import com.gira.form.PostCreateForm;
import com.gira.form.PostFilterForm;
import com.gira.form.PostUpdateForm;
import com.gira.repository.PostRepository;

import com.gira.specification.PostSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PostDto> findAll(PostFilterForm form,  Pageable pageable) {
        Specification spec = PostSpecification.buildSpec(form);
        return postRepository.findAll(spec, pageable)
                .map(post -> modelMapper.map(post, PostDto.class));
    }

    @Override
    public PostDto create(PostCreateForm form) {
        Post post = modelMapper.map(form, Post.class); // dùng modelMapper map từ form sang Post entity
        Post savePost = postRepository.save(post);
        return modelMapper.map(savePost, PostDto.class); // dùng modelMapper map từ savePost sang PostDTO
    }

    @Override
    public PostDto findById(Long id) {
      return postRepository.findById(id)
                .map(post -> modelMapper.map(post, PostDto.class))
              .orElse(null);

    }

    @Override
    public PostDto update(Long id, PostUpdateForm form) {
        Post post = postRepository.findById(id).orElse(null);
        modelMapper.map(form, post); // set dữ liệu ng dùng nhập vào dữ liệu cũ
        Post savePost = postRepository.save(post);
        return modelMapper.map(savePost, PostDto.class);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

}
