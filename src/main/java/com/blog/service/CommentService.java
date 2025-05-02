package com.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.model.Comment;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment addComment(Long postId, String content, String username ){
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findByEmail(username).orElseThrow();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
        return comment;    
    }

    public List<Comment> getCommentsForPost(Long postId){
        return commentRepository.findByPostId(postId);
    }
}
