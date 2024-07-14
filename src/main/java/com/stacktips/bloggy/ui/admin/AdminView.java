package com.stacktips.bloggy.ui.admin;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;


public abstract class AdminView extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        DashboardLayout instance = DashboardLayout.getInstance();
        if (instance != null) {
            instance.setTitle(getTitle());
        }
    }

    protected abstract String getTitle();

}
