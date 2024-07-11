package com.stacktips.bloggy.api;

import com.stacktips.bloggy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

//    @GetMapping
//    public List<Post> getAllPosts() {
//        return postRepository.findAll();
//    }
//
//    @PostMapping
//    public Post createPost(@RequestBody Post post) {
//        post.setDateCreated(LocalDateTime.now());
//        return postRepository.(post);
//    }
//
//    @PutMapping("/{id}")
//    public Post updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
//        Post post = postRepository.findById(id).orElseThrow();
//        post.setTitle(postDetails.getTitle());
//        post.setContent(postDetails.getContent());
//        post.setDateUpdated(LocalDateTime.now());
//        return postRepository.save(post);
//    }
}
