package com.stacktips.bloggy.security;

import com.stacktips.bloggy.repository.UserRepository;
import com.stacktips.bloggy.service.UserService;
import com.stacktips.bloggy.ui.auth.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class VaadinSecurityConfig extends VaadinWebSecurity {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/VAADIN/**", "/META-INF/**", "/static/**", "/error/**", "/accessDenied/**", "/vaadinServlet/**").permitAll()
                        .requestMatchers("/", "/login", "/register", "/error").permitAll()
//                auth.requestMatchers( request ->).permitAll()
        );

//        http
//                .cors(withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(req -> req
//                        .requestMatchers("/", "/login", "/register", "/error").permitAll()
//                        .requestMatchers("/VAADIN/**", "/META-INF/**", "/static/**", "/error/**", "/accessDenied/**", "/vaadinServlet/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/")
//                        .permitAll()
//                ).logout(logout -> logout.permitAll()
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout"))
//        ;

        super.configure(http);

        // This is important to register your login view to the
        // navigation access control mechanism:
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Customize your WebSecurity configuration.
        super.configure(web);
    }

    @Bean
    UserDetailsManager userDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserService(userRepository, passwordEncoder);
    }

//    @Bean
//    public UserDetailsManager userDetailsService() {
//        UserDetails user =
//                User.withUsername("user")
//                        .password("{noop}user")
//                        .roles("USER")
//                        .build();
//        UserDetails admin =
//                User.withUsername("admin")
//                        .password("{noop}admin")
//                        .roles("ADMIN")
//                        .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}