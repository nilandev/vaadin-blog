package com.stacktips.bloggy.ui.admin;

import com.stacktips.bloggy.ui.admin.category.CategoriesListView;
import com.stacktips.bloggy.ui.admin.post.PostsListView;
import com.stacktips.bloggy.ui.admin.tags.TagsListView;
import com.stacktips.bloggy.ui.admin.user.UsersListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

@CssImport("./styles/admin.css")
public class DashboardLayout extends AppLayout {

    private final H1 title;

    public DashboardLayout() {
        DrawerToggle toggle = new DrawerToggle();

        title = new H1("Bloggy");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav sideNav = new SideNav();
        sideNav.setLabel("Manage");
        sideNav.addItem(
                new SideNavItem("Dashboard", DashboardView.class, VaadinIcon.DASHBOARD.create()),
                new SideNavItem("Users", UsersListView.class, VaadinIcon.USER_CHECK.create()),
                new SideNavItem("Posts", PostsListView.class, VaadinIcon.FILE_TEXT_O.create()),
                new SideNavItem("Categories", CategoriesListView.class, VaadinIcon.RECORDS.create()),
                new SideNavItem("Tags", TagsListView.class, VaadinIcon.TAGS.create())
        );

        sideNav.setClassName("side-nav");

        SideNav sideNav2 = new SideNav();
        sideNav2.setLabel("Manage");
        SideNavItem sideNavItem = new SideNavItem("View site", com.stacktips.bloggy.ui.routes.PostListView.class, VaadinIcon.LINK.create());
        sideNavItem.setOpenInNewBrowserTab(true);
        sideNav2.addItem(sideNavItem);

        Image image = new Image("icons/icon.png", "");
        image.setHeight("30px");
        H2 logo = new H2("Bloggy");
        logo.addClassName("logo");

        HorizontalLayout logoContainer = new HorizontalLayout(image, logo);
        logoContainer.setSpacing(false);

        Scroller scroller = new Scroller(new VerticalLayout(logoContainer, sideNav, sideNav2));
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title);
        setPrimarySection(Section.DRAWER);
        getElement().setAttribute("theme", "no-navbar-border");
    }

    public void setTitle(String newTitle) {
        title.setText(newTitle);
    }

    public static DashboardLayout getInstance() {
        return (DashboardLayout) UI.getCurrent().getChildren()
                .filter(component -> component.getClass() == DashboardLayout.class).findFirst().orElse(null);
    }
}