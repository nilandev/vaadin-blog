package com.stacktips.bloggy.ui.layout;

import com.stacktips.bloggy.security.SecurityService;
import com.stacktips.bloggy.ui.auth.RegisterView;
import com.stacktips.bloggy.ui.routes.PostDetailView;
import com.stacktips.bloggy.ui.routes.PostListView;
import com.vaadin.flow.component.ScrollOptions;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    private SecurityService securityService;

    public MainLayout() {
        createNavbar();
    }

    private void createNavbar() {
        H1 logo = new H1("Bloggy");
        logo.addClassName("logo");

        RouterLink link = new RouterLink(PostListView.class);
        link.add(logo);

        HorizontalLayout headerContainer = new HorizontalLayout();
        headerContainer.setWidthFull();
        headerContainer.addClassNames("header");
        headerContainer.setSpacing(true);

        HorizontalLayout header = new HorizontalLayout(link);
        header.addClassNames("container");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.add(getNavBar());

        headerContainer.add(header);
        addToNavbar(headerContainer);
    }

    private HorizontalLayout getNavBar() {
        Anchor logout = new Anchor("/logout", "Log out");
        HorizontalLayout navbarLayout = new HorizontalLayout(
                new RouterLink("Blog", PostListView.class),
                new RouterLink("Register", RegisterView.class),
                new Button("Logout", click -> securityService.logout()));
        navbarLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        navbarLayout.setWidth("100%");
        navbarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        navbarLayout.addClassNames("navbar");
        return navbarLayout;
    }
}
