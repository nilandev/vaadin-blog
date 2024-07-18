package com.stacktips.bloggy.ui.admin.post;

import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.ui.admin.AdminView;
import com.stacktips.bloggy.ui.admin.DashboardLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridLazyDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value = "admin/posts", layout = DashboardLayout.class)
@PageTitle("Posts")
@UIScope
public class PostsListView extends AdminView {

    private final PostService postService;
    private final Grid<Post> grid = new Grid<>(Post.class);
    private GridLazyDataView<Post> lazyDataView;

    @Autowired
    public PostsListView(PostService postService) {
        this.postService = postService;

        addActions();
        grid.setHeight("800px");
        grid.getElement().setAttribute("theme", "no-border");

        grid.addColumn(Post::getId).setHeader("ID");
        grid.addColumn(Post::getTitle).setHeader("Title");
        grid.addColumn(Post::getSlug).setHeader("Slug");
        grid.addColumn(Post::getExcerpt).setHeader("Excerpt");
        grid.addColumn(Post::getPublishedDate).setHeader("Published Date");
        grid.addColumn(Post::getPostStatus).setHeader("Post Status");
        grid.setColumns("id", "title", "postStatus");

        grid.addColumn(
                new ComponentRenderer<>(HorizontalLayout::new, (layout, post) -> {
                    Button edit = new Button();
                    edit.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL,
                            ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_TERTIARY);
                    edit.addClickListener(e -> this.editPost(edit, post));
                    edit.setIcon(new Icon(VaadinIcon.EDIT));

                    Button delete = new Button();
                    delete.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL,
                            ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
                    delete.addClickListener(e -> this.deletePost(post));
                    delete.setIcon(new Icon(VaadinIcon.TRASH));
                    layout.add(edit, delete);
                })).setHeader("Manage");

        add(grid);
        setupGridDataProvider();
    }

    private void setupGridDataProvider() {
        CallbackDataProvider<Post, Void> dataProvider = new CallbackDataProvider<>(
            query -> {
                int offset = query.getOffset();
                int limit = query.getLimit();
                int page = offset / limit;
                return postService.findAll(page, limit).stream();
            },
            query -> (int) postService.count()
        );

        lazyDataView = grid.setItems(dataProvider);
    }

    private void addActions() {
        HorizontalLayout actions = new HorizontalLayout();
        actions.setSizeFull();
        actions.setAlignItems(FlexComponent.Alignment.CENTER);
        actions.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        Button newPostButton = new Button("New", new Icon(VaadinIcon.PLUS));
        newPostButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        newPostButton.addClickListener(event -> newPostButton.getUI()
                .ifPresent(ui -> ui.navigate(EditPostView.class)));
        actions.add(newPostButton);
        add(actions);
    }

    private void editPost(Button button, Post post) {
        if (post != null) {
            QueryParameters urlParams = new QueryParameters(Map.of("id", List.of(String.valueOf(post.getId()))));
            button.getUI().ifPresent(ui -> ui.navigate(EditPostView.class, urlParams));
        } else {
            button.getUI().ifPresent(ui -> ui.navigate(EditPostView.class));
        }
    }

    private void deletePost(Post post) {
        postService.deletePost(post.getId());
        lazyDataView.refreshAll();
    }

    @Override
    protected String getTitle() {
        return "Posts";
    }
}
