package com.narcis.quillr.controller;

import com.narcis.quillr.model.Post;
import com.narcis.quillr.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    public record CreateRequest(Long userId, String title, String content) {}
    public record UpdateRequest(String title, String content) {}

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<Post>> getAllPostByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody CreateRequest request){
        boolean post = postService.createPost(request.userId(), request.title(), request.content());
        if (post){
            return ResponseEntity.ok().body("Post created!");
        }
        return ResponseEntity.badRequest().body("Post cannot be created!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody UpdateRequest request){
        boolean post = postService.updatePost(id, request.title(), request.content());
        if (post){
            return ResponseEntity.ok().body("Post updated!");
        }
        return ResponseEntity.badRequest().body("Post cannot be updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        boolean post = postService.deletePost(id);
        if (post){
            return ResponseEntity.ok().body("Post deleted!");
        }
        return ResponseEntity.badRequest().body("Post cannot be deleted!");
    }
}
