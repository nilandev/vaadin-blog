package com.stacktips.bloggy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String bio;

    @Column(length = 60)
    private String password;

    private String roles;

    public Set<Role> getRoles() {
        return Arrays.stream(roles.split(","))
                .map(Role::valueOf).collect(Collectors.toSet());
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles.stream()
                .map(Role::name)
                .collect(Collectors.joining(","));
    }

    public void setRole(Role role) {
        Set<Role> currentRoles = getRoles();
        currentRoles.add(role);
        setRoles(currentRoles);
    }

    public enum Role {
        REGULAR_USER, AUTHOR, ADMIN
    }
}
