package com.stacktips.bloggy.repository;

import com.stacktips.bloggy.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {}
