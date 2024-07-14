package com.stacktips.bloggy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
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
