package com.stacktips.bloggy.ui.admin;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;


@Route(value = "admin", layout = DashboardLayout.class)
@PageTitle("Post Details")
@PermitAll
public class DashboardView extends AdminView {

    public DashboardView() {

    }

    @Override
    protected String getTitle() {
        return "Dashboard";
    }
}