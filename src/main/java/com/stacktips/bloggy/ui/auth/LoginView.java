package com.stacktips.bloggy.ui.auth;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
@CssImport("./styles/shared-styles.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm loginForm = new LoginForm();

    public LoginView() {
        setSizeFull();
        getStyle().set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        add(loginForm);
        loginForm.getElement().setAttribute("no-autofocus", "");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
