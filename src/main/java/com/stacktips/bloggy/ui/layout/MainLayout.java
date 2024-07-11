package com.stacktips.bloggy.ui.layout;

import com.stacktips.bloggy.ui.routes.NewPostView;
import com.stacktips.bloggy.ui.routes.PostListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createNavbar();
    }

    private void createNavbar() {
        H1 logo = new H1("Bloggy");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(logo);
        header.addClassNames("container");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        header.add(getNavBar());
        addToNavbar(header);
    }

    private HorizontalLayout getNavBar() {
        RouterLink postListLink = new RouterLink("Posts", PostListView.class);
        RouterLink newPostLink = new RouterLink("New Post", NewPostView.class);

        HorizontalLayout navbar = new HorizontalLayout(postListLink, newPostLink);
        navbar.setSpacing(true);
        navbar.setPadding(true);
        return navbar;
    }
}
