package com.narcis.quillr.repository;

import com.narcis.quillr.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long>,PagingAndSortingRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findByTitleContainingIgnoreCase(String title);
    Page<Post> findByUserId(Long userId, Pageable pageable);
}
