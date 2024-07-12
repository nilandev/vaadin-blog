package com.stacktips.bloggy.ui.form;

import com.stacktips.bloggy.model.Author;
import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.model.Post;
import com.stacktips.bloggy.model.Tag;
import com.stacktips.bloggy.service.AuthorService;
import com.stacktips.bloggy.service.CategoryService;
import com.stacktips.bloggy.service.PostService;
import com.stacktips.bloggy.service.TagService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.Set;

public class PostForm extends FormLayout {
    private TextField title = new TextField("Title");
    private TextField slug = new TextField("Slug");
    private TextField excerpt = new TextField("Excerpt");
//    private DatePicker dateCreated = new DatePicker("Date Created");
//    private DatePicker dateUpdated = new DatePicker("Date Updated");
//    private DatePicker publishedDate = new DatePicker("Published Date");
    private TextField postStatus = new TextField("Post Status");
    private TextArea content = new TextArea("Content");
    private TextField thumbnailUrl = new TextField("Thumbnail URL");
    private TextField videoId = new TextField("Video ID");
    private TextField sourceCode = new TextField("Source Code");
    private TextField postType = new TextField("Post Type");
    private TextField templateType = new TextField("Template Type");
    private TextField displayOrder = new TextField("Display Order");

    private ComboBox<Author> author = new ComboBox<>("Author");
    private ComboBox<Set<Tag>> tags = new ComboBox<>("Tags");
    private ComboBox<Set<Category>> categories = new ComboBox<>("Categories");

    private Button save = new Button("Save");

    private final PostService postService;
    private final AuthorService authorService;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final Binder<Post> binder = new BeanValidationBinder<>(Post.class);

    public PostForm(PostService postService, AuthorService authorService, TagService tagService, CategoryService categoryService) {
        setClassName("container");
        this.postService = postService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.categoryService = categoryService;

        binder.bindInstanceFields(this);

        author.setItems(authorService.findAll());
        author.setItemLabelGenerator(Author::getName);

//        ListDataProvider<Tag> dataProvider = DataProvider.ofCollection(tagService.findAll());
//        tags.setDataProvider(dataProvider);

        tags.setItems(tagService.findAll());
//        tags.setItemLabelGenerator((item -> );

        categories.setItems(categoryService.findAll());
//        categories.setItemLabelGenerator(category -> category.getName());

        save.addClickListener(e -> savePost());

//        add(title, slug, excerpt, dateCreated, dateUpdated, publishedDate, postStatus, content, thumbnailUrl, videoId, sourceCode, postType, templateType, displayOrder, author, tags, categories, save);
        add(title, slug, excerpt, postStatus, content, thumbnailUrl, videoId, sourceCode, postType, templateType, displayOrder, author, tags, categories, save);
    }

    public void setPost(Post post) {
        binder.setBean(post);
    }

    private void savePost() {
        Post post = binder.getBean();
//        post.setDateCreated(dateCreated.getValue().atStartOfDay());
//        post.setDateUpdated(dateUpdated.getValue().atStartOfDay());
//        post.setPublishedDate(publishedDate.getValue().atStartOfDay());
        postService.save(post);
    }
}


