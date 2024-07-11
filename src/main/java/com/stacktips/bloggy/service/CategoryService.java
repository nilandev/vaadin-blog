package com.stacktips.bloggy.service;

import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Set<Category> findAll() {
        return categoryRepository.findAll().stream().collect(Collectors.toSet());
    }
}