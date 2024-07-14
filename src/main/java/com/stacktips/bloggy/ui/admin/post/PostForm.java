package com.stacktips.bloggy.ui.admin.post;

import com.stacktips.bloggy.model.Author;
import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.model.Tag;
import com.stacktips.bloggy.service.AuthorService;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.service.TagService;
import com.stacktips.bloggy.ui.admin.tags.TagsListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PostForm extends FormLayout {
    private final PostService postService;
    private final AuthorService authorService;
    private final TagService tagService;
    private final CategoryService categoryService;

    private TextField title = new TextField("Title");
    private TextField slug = new TextField("Slug");
    private TextField excerpt = new TextField("Excerpt");
    private TextField postStatus = new TextField("Post Status");
    private TextArea content = new TextArea("Content");
    private TextField thumbnailUrl = new TextField("Thumbnail URL");
    private TextField videoId = new TextField("Video ID");
    private TextField sourceCode = new TextField("Source Code");
    private TextField postType = new TextField("Post Type");
    private TextField templateType = new TextField("Template Type");
    private TextField displayOrder = new TextField("Display Order");
    private ComboBox<Author> author = new ComboBox<>("Author");
    private MultiSelectComboBox<Tag> tags = new MultiSelectComboBox<>("Tags");
    private MultiSelectComboBox<Category> categories = new MultiSelectComboBox<>("Categories");
    private Button save = new Button("Save");

    private final Binder<Post> binder = new Binder<>(Post.class);

    public PostForm(PostService postService, AuthorService authorService, TagService tagService,
                    CategoryService categoryService, Post post) {
        setClassName("container");
        this.postService = postService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.categoryService = categoryService;

        author.setItems(authorService.findAll().stream().collect(Collectors.toSet()));
        author.setItemLabelGenerator(Author::getName);

        tags.setItems(tagService.findAll());
        tags.setItemLabelGenerator(tag -> "#" + tag.getName());

        categories.setItems(categoryService.findAll());
        categories.setItemLabelGenerator(Category::getName);

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
//        binder.forField(displayOrder).bind((post) -> post.getDisplayOrder().toString(), (post)-> post.setDisplayOrder());
        binder.forField(author).bind(Post::getAuthor, Post::setAuthor);
        // Assuming tags and categories are stored as Set<Tag> and Set<Category>
        // You may need custom binding for tags and categories
        // For simplicity, using bind() without custom converters


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


