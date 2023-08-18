package com.payoya.diplomaproject.api.controller;

import com.payoya.diplomaproject.api.entity.Post;
import com.payoya.diplomaproject.api.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostRestController {

    private PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public List<Post> findAllPosts(){
        return postService.findAllPosts();
    }

    @GetMapping("/{id}")
    public Post findPostById(@PathVariable Long id){
        return postService.findPostById(id);
    }

    @PostMapping("/new")
    public Post createNewPost(@RequestBody Post post){
        return postService.createNewPost(post);
    }

    @PostMapping("/new/{id}")
    public Post createNewPostWithUserId(@RequestBody Post post, @PathVariable Long id){
        return postService.createNewPostWithUserId(post, id);
    }
}
