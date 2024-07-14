package com.stacktips.bloggy.ui.admin;

import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.service.AuthorService;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.service.TagService;
import com.stacktips.bloggy.ui.form.PostForm;
import com.stacktips.bloggy.ui.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "admin/post/new", layout = MainLayout.class)
public class NewPostView extends VerticalLayout {


    public NewPostView(PostService postService, AuthorService authorService,
                       TagService tagService, CategoryService categoryService) {
        setClassName("container");

        PostForm postForm = new PostForm(postService, authorService, tagService, categoryService);
        postForm.setPost(new Post());
        add(postForm);
    }
}

