package com.stacktips.bloggy.security;

import com.stacktips.bloggy.repository.UserRepository;
import com.stacktips.bloggy.service.UserService;
import com.stacktips.bloggy.ui.auth.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
//        http
//                .cors(withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(req -> req
//                        .requestMatchers("/", "/login", "/register", "/error").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/")
//                        .permitAll()
//                ).logout(logout -> logout.permitAll());
////                        .logoutUrl("/logout")
////                        .logoutSuccessUrl("/login?logout"));
//
//        return http.build();
//
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/", "/login", "/register", "/error").permitAll()
                        .requestMatchers("/VAADIN/**", "/META-INF/**", "/static/**", "/error/**", "/accessDenied/**", "/vaadinServlet/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                ).logout(logout -> logout.permitAll()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout"));

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserService(userRepository, passwordEncoder);
    }

}
