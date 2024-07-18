package com.stacktips.bloggy.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
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

    public Set<Role> getRolesSet() {
        if (!StringUtils.hasText(roles)) {
            return new HashSet<>();
        }

        return Arrays.stream(roles.split(","))
                .map(Role::valueOf).collect(Collectors.toSet());
    }

    public void addRole(Set<Role> roles) {
        this.roles = roles.stream()
                .map(Role::name)
                .collect(Collectors.joining(","));
    }

    public void setRole(Role role) {
        Set<Role> currentRoles = getRolesSet();
        currentRoles.add(role);
        addRole(currentRoles);
    }

    public enum Role {
        REGULAR_USER, AUTHOR, ADMIN
    }
}
