package com.stacktips.bloggy.ui.auth;

import com.stacktips.bloggy.model.User;
import com.stacktips.bloggy.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("register")
@PageTitle("Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    @Autowired
    public RegisterView(UserService userService) {
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        TextField email = new TextField("Email");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmPassword = new PasswordField("Confirm Password");

        Button registerButton = new Button("Register", event -> {
            if (!password.getValue().equals(confirmPassword.getValue())) {
                Notification.show("Passwords do not match");
                return;
            }

            User user = new User();
            user.setFirstName(firstName.getValue());
            user.setLastName(lastName.getValue());
            user.setEmail(email.getValue());
            user.setPassword(password.getValue());
            user.setRole(User.Role.REGULAR_USER);

            userService.saveUser(user);

            Notification.show("Registration successful");
            getUI().ifPresent(ui -> ui.navigate("login"));
        });

        FormLayout formLayout = new FormLayout(firstName, lastName, email, password, confirmPassword, registerButton);
        add(formLayout);
    }
}
