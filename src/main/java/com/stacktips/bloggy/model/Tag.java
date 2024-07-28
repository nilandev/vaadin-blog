package com.stacktips.bloggy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag",
        indexes = {
                @Index(name = "tag_name_index", columnList = "name", unique = true)
        }
)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private Set<Post> posts;

}
