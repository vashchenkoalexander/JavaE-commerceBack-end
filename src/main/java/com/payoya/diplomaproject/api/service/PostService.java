package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.Post;
import com.payoya.diplomaproject.api.repository.IPostRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private IPostRepository postRepository;

    private UserService userService;

    PostService(IPostRepository postRepository, UserService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('admin:read')")
    public List<Post> findAllPosts(){
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(new Post());
    }

    public Post createNewPost(Post post){
        post.setDateOfCreate(LocalDateTime.now().withNano(0));
        return postRepository.save(post);
    }

    /*
    Create new post for User. But you can create only when you have a username and password for user
     to whom you want to assign this post
     */
    @PostAuthorize("returnObject.user.username == principal.username")
    public Post createNewPostWithUserId(Post post, Long id){
        post.setUser(userService.findUserById(id));
        return createNewPost(post);
    }

}
