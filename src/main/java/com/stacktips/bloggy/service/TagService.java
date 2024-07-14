package com.stacktips.bloggy.service;

import com.stacktips.bloggy.model.Tag;
import com.stacktips.bloggy.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Set<Tag> findAll() {
        return tagRepository.findAll().stream().collect(Collectors.toSet());

    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public Optional<Tag> findTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag saveTag(Tag category) {
        return tagRepository.save(category);

    }
}
