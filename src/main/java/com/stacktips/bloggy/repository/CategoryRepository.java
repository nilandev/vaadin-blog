package com.stacktips.bloggy.repository;

import com.stacktips.bloggy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
