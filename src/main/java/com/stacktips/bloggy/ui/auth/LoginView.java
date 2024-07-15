package com.stacktips.bloggy.ui.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        login.setAction("login");

        add(new H1("Test Application"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
//public class LoginView extends VerticalLayout {
//
//    public LoginView() {
//        H1 title = new H1("Login");
//
//        TextField email = new TextField("Email");
//        PasswordField password = new PasswordField("Password");
//
//        Button loginButton = new Button("Login", event -> {
//            // The actual login logic will be handled by Spring Security
//            Notification.show("Login attempt", 2000, Notification.Position.TOP_END);
//        });
//
//        FormLayout formLayout = new FormLayout(email, password, loginButton);
//        add(title, formLayout);
//    }
//}
