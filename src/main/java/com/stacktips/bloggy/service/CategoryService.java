package com.stacktips.bloggy.service;

import com.stacktips.bloggy.model.Category;
import com.stacktips.bloggy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Set<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().collect(Collectors.toSet());
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
}