package com.stacktips.bloggy.ui.admin;

import com.stacktips.bloggy.ui.admin.category.CategoryView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class DashboardLayout extends AppLayout {

    public DashboardLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("MyApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav nav = getSideNav();

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title);
        setPrimarySection(Section.DRAWER);
    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Users", "/users", VaadinIcon.USER_CHECK.create()),
                new SideNavItem("Posts", "/orders", VaadinIcon.FILE_TEXT_O.create()),
                new SideNavItem("Categories", CategoryView.class, VaadinIcon.RECORDS.create()),
                new SideNavItem("Tags", "/products", VaadinIcon.TAGS.create()));
        return sideNav;
    }
}