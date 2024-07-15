package com.stacktips.bloggy.ui.routes;

import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.ui.layout.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "post/:postId", layout = MainLayout.class)
@PageTitle("Post Details")
@AnonymousAllowed
public class PostDetailView extends VerticalLayout implements BeforeEnterObserver {

    private final PostService postService;
    private final CategoryService categoryService;

    @Autowired
    public PostDetailView(PostService postService, CategoryService categoryService) {
        setClassName("container");
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        final RouteParameters urlParameters = beforeEnterEvent.getRouteParameters();
        String postId = urlParameters.get("postId")
                .orElseThrow(() -> new NotFoundException("Page not found"));

        Post post = postService.findById(Long.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Post not found"));

        HorizontalLayout mainLayout = new HorizontalLayout(renderContent(post), renderSidebar(post));
        mainLayout.setClassName("post-content-container");
        mainLayout.setSizeFull();
        add(mainLayout);
    }

    private Component renderSidebar(Post post) {
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");
        sidebar.setWidth("30%");
        sidebar.add(createTopCategoriesWidget());
        return sidebar;
    }

    private VerticalLayout renderContent(Post post) {
        VerticalLayout postContentLayout = new VerticalLayout();
        postContentLayout.addClassName("main-content");

        H1 title = new H1(post.getTitle());
        title.addClassName("display1");
        postContentLayout.add(title);

        if (post.getThumbnailUrl() != null && !post.getThumbnailUrl().isEmpty()) {
            Image thumbnail = new Image(post.getThumbnailUrl(), post.getTitle() + " thumbnail");
            postContentLayout.add(thumbnail);
        }

        Paragraph excerpt = new Paragraph(post.getExcerpt());
        excerpt.addClassNames("lead");
        postContentLayout.add(excerpt);

        Html content = new Html("<div class='post-content'>" + post.getContent() + "</div>");
        postContentLayout.add(content);

        postContentLayout.setWidth("70%");
        return postContentLayout;
    }

    private VerticalLayout createTopCategoriesWidget() {
        List<Category> topCategories = categoryService.findTop10Categories();
        VerticalLayout categoriesLayout = new VerticalLayout();
        categoriesLayout.add(new H3("Top 10 Categories"));

        for (Category category : topCategories) {
            categoriesLayout.add(new Span(category.getName()));
        }

        return categoriesLayout;
    }
}
