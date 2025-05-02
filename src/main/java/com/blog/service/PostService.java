package com.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.model.Post;
import com.blog.model.User;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;

import jakarta.persistence.PostRemove;

@Service
public class PostService{

    @Autowired
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post create(Post post, String username){
        User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usernname not found"));
                    post.setUser(user);
        return postRepository.save(post);
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    public Post updatePost(Long id, Post updatedPost){
        return postRepository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            return postRepository.save(post);
        }).orElseThrow(()-> new RuntimeException("Post not found"));
    }
    
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }
}