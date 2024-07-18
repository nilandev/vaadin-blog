package com.stacktips.bloggy.ui.admin.category;

import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.ui.admin.DashboardLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.Optional;

@PermitAll
@Route(value = "admin/category/manage", layout = DashboardLayout.class)
public class EditCategoryView extends VerticalLayout implements BeforeEnterObserver {

    private CategoryService categoryService;
    private Category currentCategory;

    private TextField name = new TextField("Name");
    private TextField slug = new TextField("Slug");
    private TextArea excerpt = new TextArea("Excerpt");
    private TextField publishStatus = new TextField("Publish Status");
    private IntegerField articleCount = new IntegerField("Article Count");
    private Button saveButton = new Button("Save");

    private Binder<Category> binder = new Binder<>(Category.class);

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> categoryId = event.getLocation().getQueryParameters()
                .getParameters()
                .getOrDefault("id", List.of())
                .stream()
                .findFirst();
        if (categoryId.isPresent()) {
            Long id = Long.parseLong(categoryId.get());
            categoryService.findCategoryById(id).ifPresent(category -> {
                currentCategory = category;
                binder.readBean(currentCategory);
            });
        }
    }

    public EditCategoryView(CategoryService categoryService) {
        this.categoryService = categoryService;

        FormLayout formLayout = new FormLayout();
        formLayout.add(name, slug, excerpt, publishStatus, articleCount);

        binder.bindInstanceFields(this);

        saveButton.addClickListener(e -> saveCategory());
        add(formLayout, saveButton);
    }

    public void setCategory(Category category) {
        binder.readBean(category);
    }

    private void saveCategory() {
        if (currentCategory == null) {
            currentCategory = new Category();
        }

        Category category = new Category();
        if (binder.writeBeanIfValid(category)) {
            categoryService.saveCategory(category);
            UI.getCurrent().navigate(CategoriesListView.class);
            System.out.println("Category saved: " + category);
        } else {
            System.out.println("Validation errors occurred");
        }
    }

}
