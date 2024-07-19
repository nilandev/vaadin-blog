package com.stacktips.bloggy.ui.admin.tags;

import com.stacktips.bloggy.model.Tag;
import com.stacktips.bloggy.service.TagService;
import com.stacktips.bloggy.ui.admin.AdminView;
import com.stacktips.bloggy.ui.admin.AdminLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

@Route(value = "admin/tags/manage", layout = AdminLayout.class)
public class EditTagView extends AdminView implements BeforeEnterObserver {

    private final TextField name = new TextField("Name");
    private final Button saveButton = new Button("Save");

    private TagService tagService;
    private Tag currentTag;

    private Binder<Tag> binder = new Binder<>(Tag.class);

    public EditTagView(TagService tagService) {
        this.tagService = tagService;

        FormLayout formLayout = new FormLayout();
        formLayout.setClassName("container");
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("900px", 2, FormLayout.ResponsiveStep.LabelsPosition.ASIDE));
        formLayout.add(name);

        binder.bindInstanceFields(this);

        saveButton.addClickListener(e -> saveTag());
        add(formLayout, saveButton);
    }

    public void setTag(Tag tag) {
        binder.readBean(tag);
    }

    private void saveTag() {
        if (currentTag == null) {
            currentTag = new Tag();
        }

        Tag tag = new Tag();
        if (binder.writeBeanIfValid(tag)) {
            tagService.saveTag(tag);
            Notification.show("Tag saved successfully", 2000, Notification.Position.TOP_END);
            UI.getCurrent().navigate(TagsListView.class);
        } else {
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
            tagService.findTagById(id).ifPresent(tag -> {
                currentTag = tag;
                binder.readBean(currentTag);
            });
        }
        super.beforeEnter(event);
    }

    @Override
    protected String getTitle() {
        if (null != currentTag) {
            return "Manage tag #" + currentTag.getId();
        }
        return "New tag";
    }
}
