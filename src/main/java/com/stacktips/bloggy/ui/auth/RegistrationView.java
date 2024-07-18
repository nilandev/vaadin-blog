package com.stacktips.bloggy.ui.auth;

import com.stacktips.bloggy.service.UserService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("register")
@PageTitle("Register")
@AnonymousAllowed
@CssImport("./styles/shared-styles.css")
public class RegistrationView extends VerticalLayout {

    public RegistrationView(UserService userService) {
        setSizeFull();
        getStyle().set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        RegistrationForm registrationForm = new RegistrationForm();
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);

        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm, userService);
        registrationFormBinder.addBindingAndValidation();
    }
}