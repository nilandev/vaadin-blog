package com.stacktips.bloggy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String excerpt;
    private String publishStatus;
    private Integer articleCount;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private Set<Post> posts;
}
