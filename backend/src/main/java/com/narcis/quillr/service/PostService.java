package com.narcis.quillr.service;

import com.narcis.quillr.model.Post;
import com.narcis.quillr.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean createPost(Long userId, String title, String content){
        Post post = Post.builder().userId(userId).title(title).content(content).createdAt(LocalDateTime.now()).build();
        postRepository.save(post);
        return true;
    }

    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }

    public Iterable<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public List<Post> getPostsByUserId(Long userId){
        return postRepository.findByUserId(userId);
    }

    public boolean updatePost(Long id, String title, String content){
        Optional<Post> existingPost = postRepository.findById(id);
        if(existingPost.isPresent()){
            Post post = existingPost.get();
            if (!title.isEmpty()){post.setTitle(title);}
            if (!content.isEmpty()){post.setContent(content);}
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
            return true;
        }
        return false;
    }

    public boolean deletePost(Long id){
        if (postRepository.findById(id).isPresent()){
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
