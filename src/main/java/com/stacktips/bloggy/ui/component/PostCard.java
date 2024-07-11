
package com.stacktips.bloggy.ui.component;

import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.ui.routes.PostDetailView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;

import java.util.stream.Collectors;

public class PostCard extends Div {

    public PostCard(Post post) {
        addClassName("post-card");
        String thumbnailUrl = post.getThumbnailUrl();

        if (thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
            Image thumbnail = new Image(thumbnailUrl, post.getTitle() + " thumbnail");
            thumbnail.addClassName("post-card-thumbnail");
            add(thumbnail);
        }

        H2 title = new H2(post.getTitle());
        title.addClassName("post-card-title");

        Paragraph excerpt = new Paragraph(post.getExcerpt());
        excerpt.addClassName("post-card-excerpt");

//        Paragraph category = new Paragraph("Categories: " + String.join(", ",
//                post.getCategories().stream().map(Category::getName).collect(Collectors.toList())));
//        category.addClassName("post-card-category");
//
//        Paragraph author = new Paragraph("By " + (null != post.getAuthor() ? post.getAuthor().getName() : "-"));

        RouterLink detailLink = new RouterLink("", PostDetailView.class, new RouteParameters("postId", post.getId().toString()));
        detailLink.addClassName("post-card-link");


        detailLink.add(title);
//        add(detailLink, excerpt, category, author);
        add(detailLink, excerpt);
    }
}
