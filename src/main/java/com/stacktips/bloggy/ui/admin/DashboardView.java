package com.stacktips.bloggy.ui.admin;

import com.stacktips.bloggy.ui.layout.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "admin", layout = DashboardLayout.class)
@PageTitle("Post Details")
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        setClassName("post-details-container");
        H1 title = new H1("Dashboard");
        title.addClassName("display1");
        add(title);
    }

}