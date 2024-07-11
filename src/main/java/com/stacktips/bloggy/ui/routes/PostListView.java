package com.stacktips.bloggy.ui.routes;

import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.ui.component.PostCard;
import com.stacktips.bloggy.ui.layout.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Latest posts")
public class PostListView extends VerticalLayout {

    private final transient PostService postService;

    @Autowired
    public PostListView(PostService postService) {
        add(new H1("Blog Posts"));
        this.postService = postService;
        addClassNames("container", "post-list-view");
        listPosts();
    }

    private void listPosts() {
        VerticalLayout listContainer = new VerticalLayout();
        listContainer.setClassName("post-list-container");
        listContainer.setPadding(true);
        listContainer.setAlignItems(Alignment.START);
        postService.findPaginated(0, 30).forEach(post -> listContainer.add(new PostCard(post)));
        add(listContainer);
    }

}