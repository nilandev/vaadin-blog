package com.stacktips.bloggy.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String slug;
    private String excerpt;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private LocalDateTime publishedDate;
    private String postStatus;

    @Column(columnDefinition="TEXT")
    private String content;

    private String thumbnailUrl;
    private String videoId;
    private String sourceCode;
    private String postType;
    private String templateType;
    private Integer displayOrder;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;

    @ManyToOne
    private Author author;
}
