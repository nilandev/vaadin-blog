package com.stacktips.bloggy.ui.routes;

import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.ui.layout.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Route(value = "post/:postId", layout = MainLayout.class)
@PageTitle("Post Details")
public class PostDetailView extends VerticalLayout implements BeforeEnterObserver {

    private final PostService postService;

    @Autowired
    public PostDetailView(PostService postService) {
        this.postService = postService;
        setClassName("container");

        RouterLink backLink = new RouterLink("Back to Posts", PostListView.class);
        add(backLink);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        final RouteParameters urlParameters = beforeEnterEvent.getRouteParameters();
        String postId = urlParameters.get("postId")
                .orElseThrow(() -> new NotFoundException("Page not found"));

        Post post = postService.findById(Long.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Post not found"));

        displayPost(post);
    }

    private void displayPost(Post post) {
        H1 title = new H1(post.getTitle());
        add(title);

        if (post.getThumbnailUrl() != null && !post.getThumbnailUrl().isEmpty()) {
            Image thumbnail = new Image(post.getThumbnailUrl(), post.getTitle() + " thumbnail");
            add(thumbnail);
        }

        Paragraph content = new Paragraph(post.getContent());
        add(content);

        Paragraph author = new Paragraph("By " + (post.getAuthor() != null ? post.getAuthor().getName() : "-"));
        add(author);

        Paragraph categories = new Paragraph("Categories: " + String.join(", ",
                post.getCategories().stream().map(Category::getName).collect(Collectors.toList())));
        add(categories);
    }
}
