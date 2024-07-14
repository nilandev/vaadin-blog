package com.stacktips.bloggy.ui.admin.category;

import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.ui.admin.DashboardLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value = "admin/categories", layout = DashboardLayout.class)
@PageTitle("Categories")
public class CategoryView extends VerticalLayout {

    private final CategoryService categoryService;
    private final Grid<Category> grid = new Grid<>(Category.class);

    @Autowired
    public CategoryView(CategoryService categoryService) {
        this.categoryService = categoryService;
        grid.setColumns("id", "name", "slug", "excerpt", "publishStatus", "articleCount", "dateUpdated");
        grid.setAllRowsVisible(true);

        grid.addColumn(
                new ComponentRenderer<>(HorizontalLayout::new, (layout, category) -> {
                    Button edit = new Button();
                    edit.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL,
                            ButtonVariant.LUMO_PRIMARY,
                            ButtonVariant.LUMO_TERTIARY);
                    edit.addClickListener(e -> this.editCategory(edit, category));
                    edit.setIcon(new Icon(VaadinIcon.EDIT));

                    Button delete = new Button();
                    delete.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    delete.addClickListener(e -> this.deleteCategory(category));
                    delete.setIcon(new Icon(VaadinIcon.TRASH));
                    layout.add(edit, delete);
                })).setHeader("Manage");

        add(grid);
        updateList();
    }

    private void updateList() {
        grid.setItems(categoryService.findAll());
    }

    private void editCategory(Button button, Category category) {
        if (null != category) {
            QueryParameters urlParams = new QueryParameters(Map.of("id", List.of(String.valueOf(category.getId()))));
            button.getUI()
                    .ifPresent(ui -> ui.navigate(CategoryForm.class, urlParams));
        } else {
            button.getUI()
                    .ifPresent(ui -> ui.navigate(CategoryForm.class));
        }
    }

    private void deleteCategory(Category category) {
        categoryService.deleteCategory(category.getId());
        updateList();
    }
}
