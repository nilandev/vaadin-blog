package com.stacktips.bloggy.ui.admin.tags;

import com.stacktips.bloggy.model.Tag;
import com.stacktips.bloggy.service.TagService;
import com.stacktips.bloggy.ui.admin.AdminView;
import com.stacktips.bloggy.ui.admin.DashboardLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value = "admin/tags", layout = DashboardLayout.class)
@PageTitle("Categories")
public class TagsListView extends AdminView {

    private final TagService tagService;
    private final Grid<Tag> grid = new Grid<>(Tag.class);

    @Autowired
    public TagsListView(TagService tagService) {
        this.tagService = tagService;

        addActions();

        grid.setColumns("id", "name");
        grid.setAllRowsVisible(true);
        grid.getElement().setAttribute("theme", "no-border");
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

    private void addActions() {
        HorizontalLayout actions = new HorizontalLayout();
        actions.setSizeFull();
        actions.setAlignItems(FlexComponent.Alignment.CENTER);
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
        grid.setItems(tagService.findAll());
    }

    private void editCategory(Button button, Tag tag) {
        if (null != tag) {
            QueryParameters urlParams = new QueryParameters(Map.of("id", List.of(String.valueOf(tag.getId()))));
            button.getUI().ifPresent(ui -> ui.navigate(EditTagView.class, urlParams));
        } else {
            button.getUI().ifPresent(ui -> ui.navigate(EditTagView.class));
        }
    }

    private void deleteCategory(Tag tag) {
        tagService.deleteTag(tag.getId());
        updateList();
    }

    @Override
    protected String getTitle() {
        return "Tags";
    }
}
