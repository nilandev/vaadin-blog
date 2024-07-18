package com.stacktips.bloggy.ui.admin.user;

import com.stacktips.bloggy.model.User;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class RolesToStringConverter implements Converter<Set<User.Role>, String> {

    @Override
    public Result<String> convertToModel(Set<User.Role> roles, ValueContext context) {
        if (roles == null || roles.isEmpty()) {
            return Result.ok("");
        }
        return Result.ok(roles.stream().map(User.Role::name).collect(Collectors.joining(",")));
    }

    @Override
    public Set<User.Role> convertToPresentation(String roles, ValueContext context) {
        if (roles == null || roles.isEmpty()) {
            return Set.of();
        }
        return Arrays.stream(roles.split(","))
                .map(User.Role::valueOf)
                .collect(Collectors.toSet());
    }
}