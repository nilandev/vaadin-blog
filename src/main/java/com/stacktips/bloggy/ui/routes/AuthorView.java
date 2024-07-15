package com.stacktips.bloggy.ui.routes;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route("author")
@PageTitle("Author Page")
@Secured("ROLE_AUTHOR")
public class AuthorView extends VerticalLayout {

    public AuthorView() {
        add(new H1("Welcome, Author"));
    }
}