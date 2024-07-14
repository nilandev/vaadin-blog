package com.stacktips.bloggy.ui.layout;

import com.stacktips.bloggy.ui.routes.PostListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createNavbar();
    }

    private void createNavbar() {
        H1 logo = new H1("Bloggy");
        logo.addClassName("logo");

        RouterLink link = new RouterLink("", PostListView.class);
        link.add(logo);

        HorizontalLayout navbarContainer = new HorizontalLayout();
        navbarContainer.setWidth("100%");
        navbarContainer.addClassNames("navbar-top");

        HorizontalLayout header = new HorizontalLayout(logo);
        header.addClassNames("container");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.add(getNavBar());

        navbarContainer.add(header);
        addToNavbar(navbarContainer);
    }

    private HorizontalLayout getNavBar() {
        RouterLink postListLink = new RouterLink("Blog", PostListView.class);
        postListLink.setClassName("nav-link");


        HorizontalLayout navbarMenuContainer = new HorizontalLayout(postListLink);
        navbarMenuContainer.setSpacing(true);
        navbarMenuContainer.setPadding(true);
        return navbarMenuContainer;
    }
}
