package com.stacktips.bloggy.repository;

import com.stacktips.bloggy.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {}
