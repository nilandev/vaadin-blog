package com.stacktips.bloggy.ui.routes;

import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.ui.component.PostCard;
import com.stacktips.bloggy.ui.layout.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Latest posts")
public class PostListView extends VerticalLayout {

    private final transient PostService postService;
    private final VerticalLayout listContainer;
    private int currentPage = 0;
    private static final int PAGE_SIZE = 15;

    @Autowired
    public PostListView(PostService postService) {
        this.postService = postService;
        addClassNames("container", "post-list-view");
        listContainer = new VerticalLayout();
        listContainer.setClassName("post-list-container");
        listContainer.setPadding(true);
        listContainer.setAlignItems(Alignment.START);
        add(listContainer);

        listPosts(currentPage, PAGE_SIZE);
        createPaginationControls();
    }

    private void listPosts(int page, int size) {
        postService.findPaginated(page, size)
                .forEach(post -> {
                    PostCard postCard = new PostCard(post);
                    postCard.addClassName("animated");
                    listContainer.add(postCard);

                    // Remove the animation class after the animation is done
                    UI.getCurrent().getPage().executeJs(
                            "setTimeout(function() {$0.classList.remove('animated');}, 500);", postCard.getElement()
                    );
                });
    }

    private void createPaginationControls() {
        Button loadMoreButton = new Button("Load more..", new Icon(VaadinIcon.REFRESH));
        loadMoreButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loadMoreButton.addClickListener(event -> {
            currentPage++;
            listPosts(currentPage, PAGE_SIZE);
        });

        HorizontalLayout paginationLayout = new HorizontalLayout(loadMoreButton);
        paginationLayout.setClassName("pagination-controls");
        add(paginationLayout);
    }

}