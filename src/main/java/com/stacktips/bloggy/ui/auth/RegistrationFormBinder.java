package com.stacktips.bloggy.ui.auth;

import com.stacktips.bloggy.model.User;
import com.stacktips.bloggy.service.UserService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

public class RegistrationFormBinder {

    private RegistrationForm registrationForm;
    private UserService userService;

    private boolean enablePasswordValidation;

    public RegistrationFormBinder(RegistrationForm registrationForm, UserService userService) {
        this.registrationForm = registrationForm;
        this.userService = userService;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(registrationForm);

        // A custom validator for password fields
        binder.forField(registrationForm.getPasswordField())
                .withValidator(this::passwordValidator).bind("password");

        // The second password field is not connected to the Binder, but we
        // want the binder to re-check the password validator when the field
        // value changes. The easiest way is just to do that manually.
        registrationForm.getPasswordConfirmField().addValueChangeListener(e -> {
            // The user has modified the second field, now we can validate and show errors.
            // See passwordValidator() for how this flag is used.
            enablePasswordValidation = true;

            binder.validate();
        });

        // Set the label where bean-level error messages go
        binder.setStatusLabel(registrationForm.getErrorMessageField());

        // And finally the submit button
        registrationForm.getSubmitButton().addClickListener(event -> {
            registerUser(binder);
        });
    }

    private void registerUser(BeanValidationBinder<User> binder) {
        try {
            User user = new User();
            binder.writeBean(user);
            user.setRole(User.Role.REGULAR_USER);
            userService.saveUser(user);

            showSuccess(user);
        } catch (ValidationException exception) {
            // validation errors are already visible for each field,
            // and bean-level errors are shown in the status label.
            // We could show additional messages here if we want, do logging, etc.
        }
    }

    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = registrationForm.getPasswordConfirmField().getValue();
        if (pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    private void showSuccess(User user) {
        Notification notification =
                Notification.show("Data saved, welcome " + user.getFirstName());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        // Here you'd typically redirect the user to another view
    }

}