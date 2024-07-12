package com.stacktips.bloggy.ui.routes;

import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.ui.layout.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "post/:postId", layout = MainLayout.class)
@PageTitle("Post Details")
public class PostDetailView extends VerticalLayout implements BeforeEnterObserver {

    private final PostService postService;

    @Autowired
    public PostDetailView(PostService postService) {
        this.postService = postService;
        setClassName("post-details-container");
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
        title.addClassName("display1");
        add(title);

        if (post.getThumbnailUrl() != null && !post.getThumbnailUrl().isEmpty()) {
            Image thumbnail = new Image(post.getThumbnailUrl(), post.getTitle() + " thumbnail");
            add(thumbnail);
        }

        Paragraph excerpt = new Paragraph(post.getExcerpt());
        excerpt.addClassNames("lead");
        add(excerpt);

        Paragraph content = new Paragraph(post.getContent());
        add(content);

        HorizontalLayout actions = new HorizontalLayout();
        Button newPostButton = new Button("New Post", new Icon(VaadinIcon.PLUS));
        newPostButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newPostButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            newPostButton.getUI().ifPresent(ui -> ui.navigate(NewPostView.class));
        });
        actions.add(newPostButton);

        Button editPostButton = new Button("Edit Post", new Icon(VaadinIcon.EDIT));
        editPostButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editPostButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            editPostButton.getUI()
                    .ifPresent(ui -> ui.navigate(EditPostView.class, new RouteParameters(new RouteParam("postId", post.getId()))));
        });

        actions.add(editPostButton);
        add(actions);
    }
}
