package com.stacktips.bloggy.ui.layout;

import com.stacktips.bloggy.model.User;
import com.stacktips.bloggy.security.SecurityService;
import com.stacktips.bloggy.ui.admin.DashboardView;
import com.stacktips.bloggy.ui.auth.LoginView;
import com.stacktips.bloggy.ui.auth.RegistrationView;
import com.stacktips.bloggy.ui.routes.PostListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    private SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createNavbar();
    }

    private void createNavbar() {

        Image image = new Image("icons/icon.png", "");
        image.setHeight("30px");
        H1 logo = new H1("Bloggy");
        logo.addClassName("logo");

        HorizontalLayout logoContainer = new HorizontalLayout(image, logo);
        logoContainer.setSpacing(false);

        HorizontalLayout headerContainer = new HorizontalLayout();
        headerContainer.setWidthFull();
        headerContainer.addClassNames("header");
        headerContainer.setSpacing(true);

        HorizontalLayout header = new HorizontalLayout(logoContainer);
        header.addClassNames("container");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.add(getNavBar());

        headerContainer.add(header);
        addToNavbar(headerContainer);
    }

    private HorizontalLayout getNavBar() {
        HorizontalLayout navbarLayout = new HorizontalLayout();
        navbarLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        navbarLayout.setWidth("100%");
        navbarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        navbarLayout.addClassNames("navbar");

        if (securityService.isAuthenticated()) {
            navbarLayout.add(createUserMenu(securityService.getAuthenticatedUser()));
        } else {
            navbarLayout.add(createLoginRegisterButtons());
        }

        return navbarLayout;
    }


    private Component createLoginRegisterButtons() {
        Button loginButton = new Button("Login", event ->
                event.getSource().getUI().ifPresent(ui -> ui.navigate(LoginView.class)));
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button registerButton = new Button("Register",
                event -> event.getSource().getUI().ifPresent(ui -> ui.navigate(RegistrationView.class)));
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout layout = new HorizontalLayout(loginButton, registerButton);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.addClassName("auth-buttons");
        return layout;
    }

    private Component createUserMenu(User user) {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);


        Image userImage = new Image("https://ui-avatars.com/api/?name=" + user.getFirstName() + "+" + user.getLastName(),
                user.getFirstName() + "+" + user.getLastName());
        userImage.addClassName("nav-user-image");

        MenuItem userMenu = menuBar.addItem(userImage);
        VerticalLayout dropdown = new VerticalLayout();
        dropdown.setMinWidth("160px");

        var userName = new H5(user.getFirstName() + " " + user.getLastName());
        dropdown.add(userName);

        dropdown.add(new RouterLink("Account", PostListView.class));
        dropdown.add(new RouterLink("Profile", PostListView.class));
        dropdown.add(new RouterLink("Dashboard", DashboardView.class));
        dropdown.add(new Hr());
        Button logout = new Button("Logout", event -> securityService.logout());
        logout.addThemeVariants(ButtonVariant.LUMO_ERROR);
        dropdown.add(logout);

        userMenu.getSubMenu().add(dropdown);

        return menuBar;
    }
}
