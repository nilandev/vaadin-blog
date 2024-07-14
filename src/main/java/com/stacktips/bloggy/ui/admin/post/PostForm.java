package com.stacktips.bloggy.ui.admin.post;

import com.stacktips.bloggy.model.Author;
import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.model.Tag;
import com.stacktips.bloggy.service.AuthorService;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.service.TagService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PostForm extends FormLayout {
    private final PostService postService;

    private final TextField title = new TextField("Title");
    private final TextField slug = new TextField("Slug");
    private final TextField excerpt = new TextField("Excerpt");
    private final TextField postStatus = new TextField("Post Status");
    private final TextArea content = new TextArea("Content");
    private final TextField thumbnailUrl = new TextField("Thumbnail URL");
    private final TextField videoId = new TextField("Video ID");
    private final TextField sourceCode = new TextField("Source Code");
    private final TextField postType = new TextField("Post Type");
    private final TextField templateType = new TextField("Template Type");
    private final IntegerField displayOrder = new IntegerField("Display Order");
    private final ComboBox<Author> author = new ComboBox<>("Author");
    private final MultiSelectComboBox<Tag> tags = new MultiSelectComboBox<>("Tags");
    private final MultiSelectComboBox<Category> categories = new MultiSelectComboBox<>("Categories");

    private final Binder<Post> binder = new Binder<>(Post.class);

    public PostForm(PostService postService, AuthorService authorService, TagService tagService,
                    CategoryService categoryService, Post post) {
        this.postService = postService;

        setClassName("container");
        setResponsiveSteps(new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("900px", 2, ResponsiveStep.LabelsPosition.ASIDE));

        author.setItems(authorService.findAll().stream().collect(Collectors.toSet()));
        author.setItemLabelGenerator(Author::getName);

        tags.setItems(tagService.findAll());
        tags.setItemLabelGenerator(tag -> "#" + tag.getName());

        categories.setItems(categoryService.findAll());
        categories.setItemLabelGenerator(Category::getName);

        Button save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_TERTIARY);
        save.addClickListener(e -> savePost());

        add(title, slug, excerpt, postStatus, content, thumbnailUrl, videoId, sourceCode,
                postType, templateType, displayOrder, author, tags, categories, save);

        bindFields();
        if (post != null) {
            binder.setBean(post);
        } else {
            binder.setBean(new Post());
        }

    }

    private void bindFields() {
        binder.forField(title).bind(Post::getTitle, Post::setTitle);
        binder.forField(slug).bind(Post::getSlug, Post::setSlug);
        binder.forField(excerpt).bind(Post::getExcerpt, Post::setExcerpt);
        binder.forField(postStatus).bind(Post::getPostStatus, Post::setPostStatus);
        binder.forField(content).bind(Post::getContent, Post::setContent);
        binder.forField(thumbnailUrl).bind(Post::getThumbnailUrl, Post::setThumbnailUrl);
        binder.forField(videoId).bind(Post::getVideoId, Post::setVideoId);
        binder.forField(sourceCode).bind(Post::getSourceCode, Post::setSourceCode);
        binder.forField(postType).bind(Post::getPostType, Post::setPostType);
        binder.forField(templateType).bind(Post::getTemplateType, Post::setTemplateType);
        binder.forField(displayOrder).bind(Post::getDisplayOrder, Post::setDisplayOrder);
        binder.forField(author).bind(Post::getAuthor, Post::setAuthor);
        binder.forField(tags).bind(Post::getTags, Post::setTags);
        binder.forField(categories).bind(Post::getCategories, Post::setCategories);
    }


    private void savePost() {
        Post post = binder.getBean();
        if (binder.writeBeanIfValid(post)) {
            post.setDateUpdated(LocalDateTime.now());
            postService.save(post);
            Notification.show("Post saved successfully", 2000, Notification.Position.TOP_END);
            UI.getCurrent().navigate(PostsListView.class);
        } else {
            Notification.show("Validation errors occurred", 2000, Notification.Position.TOP_END);
        }
    }
}


