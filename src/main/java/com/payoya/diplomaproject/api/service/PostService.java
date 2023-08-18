package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.Post;
import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.repository.IPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private IPostRepository postRepository;

    private UserService userService;

    PostService(IPostRepository postRepository, UserService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> findAllPosts(){
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(new Post());
    }

    public Post createNewPost(Post post){
        return postRepository.save(post);
    }

    public Post createNewPostWithUserId(Post post, Long id){
        post.setUser(userService.findUserById(id));
        return createNewPost(post);
    }

}
