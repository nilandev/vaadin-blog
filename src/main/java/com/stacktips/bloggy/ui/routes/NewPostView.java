package com.stacktips.bloggy.ui.routes;

import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.service.AuthorService;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.service.TagService;
import com.stacktips.bloggy.ui.form.PostForm;
import com.stacktips.bloggy.ui.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "new-post", layout = MainLayout.class)
public class NewPostView extends VerticalLayout {

    private final PostService postService;
    private final AuthorService authorService;
    private final TagService tagService;
    private final CategoryService categoryService;

    public NewPostView(PostService postService, AuthorService authorService, TagService tagService, CategoryService categoryService) {
        setClassName("container");
        this.postService = postService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.categoryService = categoryService;

        PostForm postForm = new PostForm(postService, authorService, tagService, categoryService);
        postForm.setPost(new Post());
        add(postForm);
    }
}

