package com.stacktips.bloggy.repository;

import com.stacktips.bloggy.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PostRepository extends PagingAndSortingRepository<Post, Long>, JpaRepository<Post, Long> {

    Optional<Post> findBySlug(String slug);
}
