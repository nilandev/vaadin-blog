package com.stacktips.bloggy.repository;

import com.stacktips.bloggy.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
