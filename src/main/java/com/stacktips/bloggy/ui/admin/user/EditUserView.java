package com.stacktips.bloggy.ui.admin.user;

import com.stacktips.bloggy.model.User;
import com.stacktips.bloggy.service.UserService;
import com.stacktips.bloggy.ui.admin.AdminView;
import com.stacktips.bloggy.ui.admin.DashboardLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

@Route(value = "admin/users/manage", layout = DashboardLayout.class)
public class EditUserView extends AdminView implements BeforeEnterObserver {

    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final EmailField email = new EmailField("Email");
    private final PasswordField password = new PasswordField("Password");
    private final TextArea bio = new TextArea("Bio");
    private final MultiSelectComboBox<User.Role> roles = new MultiSelectComboBox<>("Roles");

    private final Button saveButton = new Button("Save");

    private UserService userService;
    private User currentUser;

    private Binder<User> binder = new Binder<>(User.class);

    public EditUserView(UserService userService) {
        this.userService = userService;

        roles.setItems(User.Role.values());
        roles.setItemLabelGenerator(User.Role::name);
        password.setReadOnly(true);

        FormLayout formLayout = new FormLayout();
        formLayout.setClassName("container");
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("900px", 2, FormLayout.ResponsiveStep.LabelsPosition.ASIDE)
        );
        formLayout.add(firstName, lastName, email, password, bio, roles);

        binder.forField(roles)
                .withConverter(new RolesToStringConverter())
                .bind(User::getRoles, User::setRoles);
        binder.bindInstanceFields(this);

        saveButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_TERTIARY);
        saveButton.addClickListener(e -> saveUser());
        formLayout.add(saveButton);
        add(formLayout);
    }

    public void setUser(User user) {
        binder.readBean(user);
    }

    private void saveUser() {
        if (currentUser == null) {
            currentUser = new User();
        }

        try {
            binder.writeBean(currentUser);
            userService.saveUser(currentUser);
            Notification.show("User saved successfully", 2000, Notification.Position.TOP_END);
            UI.getCurrent().navigate(UsersListView.class);
        } catch (ValidationException e) {
            Notification.show("Validation errors occurred", 2000, Notification.Position.TOP_END);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> _id = event.getLocation().getQueryParameters()
                .getParameters()
                .getOrDefault("id", List.of())
                .stream()
                .findFirst();
        if (_id.isPresent()) {
            Long id = Long.parseLong(_id.get());
            userService.findById(id).ifPresent(user -> {
                currentUser = user;
                binder.readBean(currentUser);
            });
        }
    }

    @Override
    protected String getTitle() {
        return "";
    }
}
