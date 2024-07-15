package com.stacktips.bloggy.ui.routes;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("error")
@PageTitle("Error")
public class ErrorView extends VerticalLayout {
    public ErrorView() {
        add(new H1("An error occurred"));
    }
}