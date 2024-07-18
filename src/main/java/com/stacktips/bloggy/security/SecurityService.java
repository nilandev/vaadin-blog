package com.stacktips.bloggy.security;

import com.stacktips.bloggy.model.User;
import com.stacktips.bloggy.service.UserService;
import com.vaadin.flow.spring.security.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationContext authenticationContext;
    private final UserService userService;

    public void logout() {
        authenticationContext.logout();
    }

    public User getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .flatMap(details -> userService.findByEmail(details.getUsername()))
                .orElse(null);
    }


    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
    }

    static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }
}
