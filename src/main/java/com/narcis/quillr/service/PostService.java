package com.narcis.quillr.service;

import com.narcis.quillr.model.Post;
import com.narcis.quillr.repository.PostRepository;
import com.narcis.quillr.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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

    public List<Post> getPostsByUsername(String username){
        return userRepository.findByUsernameIgnoreCase(username).map(user -> postRepository.findByUserId(user.getId())).orElse(List.of());
    }

    public Page<Post> getPostByUserIdPaged(Long userId, int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        return postRepository.findByUserId(userId, pageable);
    }

    public Page<Post> getAllPostsPaged(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        return postRepository.findAll(pageable);
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

    public List<Post> searchPostsByTitle(String title){
        return postRepository.findByTitleContainingIgnoreCase(title);
    }
}
