package com.stacktips.bloggy.ui.admin.user;

import com.stacktips.bloggy.model.User;
import com.stacktips.bloggy.service.UserService;
import com.stacktips.bloggy.ui.admin.AdminView;
import com.stacktips.bloggy.ui.admin.DashboardLayout;
import com.stacktips.bloggy.ui.admin.tags.EditTagView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value = "admin/users", layout = DashboardLayout.class)
@PageTitle("Categories")
public class UsersListView extends AdminView {

    private final UserService userService;
    private final Grid<User> grid = new Grid<>(User.class);

    @Autowired
    public UsersListView(UserService userService) {
        this.userService = userService;

        addActions();
        grid.setColumns("id", "firstName", "lastName", "email", "bio");

        grid.setAllRowsVisible(true);
        grid.getElement().setAttribute("theme", "no-border");
        grid.addColumn(
                new ComponentRenderer<>(HorizontalLayout::new, (layout, user) -> {
                    Button edit = new Button();
                    edit.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL,
                            ButtonVariant.LUMO_PRIMARY,
                            ButtonVariant.LUMO_TERTIARY);
                    edit.addClickListener(e -> this.editUser(edit, user));
                    edit.setIcon(new Icon(VaadinIcon.EDIT));

                    Button delete = new Button();
                    delete.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    delete.addClickListener(e -> this.deleteUser(user));
                    delete.setIcon(new Icon(VaadinIcon.TRASH));
                    layout.add(edit, delete);
                })).setHeader("Manage");

        add(grid);
        updateList();
    }

    private void addActions() {
        HorizontalLayout actions = new HorizontalLayout();
        actions.setSizeFull();
        actions.setAlignItems(Alignment.CENTER);
        actions.setJustifyContentMode(JustifyContentMode.END);

        Button newPostButton = new Button("New", new Icon(VaadinIcon.PLUS));
        newPostButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        newPostButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            newPostButton.getUI().ifPresent(ui -> ui.navigate(EditTagView.class));
        });
        actions.add(newPostButton);
        add(actions);
    }

    private void updateList() {
        grid.setItems(userService.findAll());
    }

    private void editUser(Button button, User user) {
        if (null != user) {
            QueryParameters urlParams = new QueryParameters(Map.of("id", List.of(String.valueOf(user.getId()))));
            button.getUI().ifPresent(ui -> ui.navigate(EditUserView.class, urlParams));
        } else {
            button.getUI().ifPresent(ui -> ui.navigate(EditUserView.class));
        }
    }

    private void deleteUser(User user) {
        userService.deleteUser(user.getId());
        updateList();
    }

    @Override
    protected String getTitle() {
        return "Tags";
    }
}
