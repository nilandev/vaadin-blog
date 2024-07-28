
package com.stacktips.bloggy.ui.component;

import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.ui.routes.PostDetailView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;

public class PostCard extends Div {

    public PostCard(Post post) {
        addClassName("post-card");
        String thumbnailUrl = post.getThumbnailUrl();
        if (thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
            Image thumbnail = new Image(thumbnailUrl, post.getTitle() + " thumbnail");
            thumbnail.addClassName("post-card-thumbnail");
            add(thumbnail);
        }

        VerticalLayout cardBody = new VerticalLayout();
        cardBody.addClassName("post-card-body");

        H2 title = new H2(post.getTitle());
        title.addClassName("post-card-title");

        Paragraph excerpt = new Paragraph(post.getExcerpt());
        excerpt.addClassName("post-card-excerpt");

        Paragraph author = new Paragraph("By " + (null != post.getAuthor() ? post.getAuthor().getName() : "by @Nilan"));
        author.addClassName("post-card-author");

        RouterLink detailLink = new RouterLink("", PostDetailView.class, new RouteParameters("slug", post.getSlug()));
        detailLink.addClassName("post-card-link");

        detailLink.add(title);
        cardBody.add(detailLink, excerpt, author);

        add(cardBody);
    }
}
