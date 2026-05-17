package com.narcis.quillr.controller;

import com.narcis.quillr.model.Post;
import com.narcis.quillr.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    public record CreateRequest(
            @NotNull Long userId,
            @NotBlank @Size(min=3, max=255) String title,
            @NotBlank String content) {}
    public record UpdateRequest(
            @NotBlank @Size(min=3, max=255) String title,
            @NotBlank String content) {}

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

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String query){
        return ResponseEntity.ok(postService.searchPostsByTitle(query));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Post>> getAllPostPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy){
        return ResponseEntity.ok(postService.getAllPostsPaged(page,size,sortBy));
    }

    @GetMapping("/user/{userId}/paged")
    public ResponseEntity<Page<Post>> getPostsByUserIdPaged(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy){
        return ResponseEntity.ok(postService.getPostByUserIdPaged(userId, page, size, sortBy));
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<List<Post>> getPostsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.getPostsByUsername(username));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@Valid @RequestBody CreateRequest request){
        boolean post = postService.createPost(request.userId(), request.title(), request.content());
        if (post){
            return ResponseEntity.ok().body("Post created!");
        }
        return ResponseEntity.badRequest().body("Post cannot be created!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id,@Valid @RequestBody UpdateRequest request){
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
