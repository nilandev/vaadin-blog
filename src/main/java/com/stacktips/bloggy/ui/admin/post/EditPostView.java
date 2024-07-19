package com.stacktips.bloggy.ui.admin.post;

import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.service.AuthorService;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.service.TagService;
import com.stacktips.bloggy.ui.admin.AdminView;
import com.stacktips.bloggy.ui.admin.AdminLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

@Route(value = "admin/posts/manage", layout = AdminLayout.class)
@PageTitle("Posts")
public class EditPostView extends AdminView implements BeforeEnterObserver {

    private final PostService postService;
    private final AuthorService authorService;
    private final TagService tagService;
    private final CategoryService categoryService;

    private PostForm postForm;
    private Post currentPost;

    public EditPostView(PostService postService, AuthorService authorService,
                        TagService tagService, CategoryService categoryService) {
        this.postService = postService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> _id = event.getLocation().getQueryParameters()
                .getParameters()
                .getOrDefault("id", List.of())
                .stream()
                .findFirst();
        _id.ifPresent(s -> initForm(Long.parseLong(s)));
        super.beforeEnter(event);
    }

    @Override
    protected String getTitle() {

        if (null != currentPost) {
            return "Manage post (#" + currentPost.getId() + ")";
        }
        return "New post";
    }

    private void initForm(Long postId) {
        currentPost = postService.findById(postId).orElse(null);
        postForm = new PostForm(postService, authorService, tagService, categoryService, currentPost);
        add(postForm);
    }
}
