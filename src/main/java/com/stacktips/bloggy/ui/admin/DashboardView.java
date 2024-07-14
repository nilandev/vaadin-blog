package com.stacktips.bloggy.ui.admin;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "admin", layout = DashboardLayout.class)
@PageTitle("Post Details")
public class DashboardView extends AdminView {

    public DashboardView() {

    }

    @Override
    protected String getTitle() {
        return "Dashboard";
    }
}