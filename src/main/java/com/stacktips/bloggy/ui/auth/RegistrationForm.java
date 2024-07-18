package com.stacktips.bloggy.ui.auth;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.stream.Stream;

public class RegistrationForm extends FormLayout {

    private final H3 title;
    private final TextField firstName;
    private final TextField lastName;

    private final EmailField email;
    private final PasswordField password;
    private final PasswordField passwordConfirm;
    private final Checkbox allowMarketing;

    private final Span errorMessageField;
    private final Button submitButton;

    public RegistrationForm() {

        getStyle().set("background-color", "white")
                .set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        title = new H3("Signup");
        firstName = new TextField("First name");
        lastName = new TextField("Last name");
        email = new EmailField("Email");

        allowMarketing = new Checkbox("Allow Marketing Emails?");
        allowMarketing.getStyle().set("margin-top", "10px");

        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm password");

        setRequiredIndicatorVisible(firstName, lastName, email, password,
                passwordConfirm);

        errorMessageField = new Span();
        submitButton = new Button("Join the community");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, firstName, lastName, email, password,
                passwordConfirm, allowMarketing, errorMessageField,
                submitButton);

        setMaxWidth("550px");

        // Allow the form layout to be responsive.
        // On device widths 0-490px we have one column.
        // Otherwise, we have two columns.
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

        // These components always take full width
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);
    }

    public PasswordField getPasswordField() {
        return password;
    }

    public PasswordField getPasswordConfirmField() {
        return passwordConfirm;
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

}