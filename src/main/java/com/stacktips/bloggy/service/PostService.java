
package com.stacktips.bloggy.service;

import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> findPaginated(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size));
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Page<Post> findAll(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size));
    }

    public long count() {
        return postRepository.count();
    }
}