package com.stacktips.bloggy.ui.admin;

import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.service.AuthorService;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.service.TagService;
import com.stacktips.bloggy.ui.form.PostForm;
import com.stacktips.bloggy.ui.layout.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;

@Route(value = "admin/post/:postId", layout = MainLayout.class)
public class EditPostView extends Div implements BeforeEnterObserver {

    private final transient PostService postService;
    private final transient AuthorService authorService;
    private final transient TagService tagService;
    private final transient CategoryService categoryService;

    private PostForm postForm;

    public EditPostView(PostService postService, AuthorService authorService,
                        TagService tagService, CategoryService categoryService) {
        this.postService = postService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        final RouteParameters urlParameters = beforeEnterEvent.getRouteParameters();

        String postId = urlParameters.get("postId")
                .orElseThrow(() -> new NotFoundException("Page not found"));
        initForm(postId);
    }

    private void initForm(String postId) {
        Post post = postService.findById(Long.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Post not found"));

        postForm = new PostForm(postService, authorService, tagService, categoryService);
        postForm.setPost(post);
        add(postForm);
    }


}
