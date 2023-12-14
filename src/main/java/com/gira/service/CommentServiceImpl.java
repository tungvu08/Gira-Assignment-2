package com.gira.service;

import com.gira.dto.CommentDto;
import com.gira.entity.Comment;
import com.gira.entity.Post;
import com.gira.form.CommentCreateForm;
import com.gira.form.CommentUpdateForm;
import com.gira.repository.CommentRepository;
import com.gira.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
@Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto create(Long postId, CommentCreateForm form) {
        Post post = postRepository.findById(postId).orElse(null);
        Comment comment = modelMapper.map(form, Comment.class);
        comment.setPost(post); // set comment cho bài post muốn cmt
        Comment saveComment = commentRepository.save(comment);
        return modelMapper.map(saveComment, CommentDto.class);

    }

    @Override
    public Page<CommentDto> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(comment -> modelMapper.map(comment, CommentDto.class));
    }

    @Override
    public Page<CommentDto> findByPostId(Long postId, Pageable pageable) {
        return commentRepository.findByPostId( postId, pageable)
                .map(comment -> modelMapper.map(comment, CommentDto.class));
    }

    @Override
    public CommentDto findById(Long id) {

        return commentRepository.findById(id)
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .orElse(null);
    }
    @Override
    public CommentDto update(Long CommentId, CommentUpdateForm form) {
        Comment comment = commentRepository.findById(CommentId).orElse(null);
        modelMapper.map(form, comment);
        Comment saveComment = commentRepository.save(comment);
        return modelMapper.map(saveComment, CommentDto.class);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
