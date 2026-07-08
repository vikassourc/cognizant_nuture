package com.cognizant.jwt.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Week 3 – Exercise 5 (Day 30)
 * Task: Create authentication service that returns JWT
 *
 * In-memory UserDetailsService implementation.
 * Provides hardcoded users for demonstration.
 *
 * In a production system, this would query a database
 * using Spring Data JPA / UserRepository.
 *
 * Predefined users:
 *   username: admin    password: admin123    role: ADMIN
 *   username: user     password: user123     role: USER
 *   username: vikas    password: password    role: USER
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /** In-memory user store: username → { password, role } */
    private static final Map<String, String[]> USER_STORE = Map.of(
            "admin", new String[]{"admin123",  "ROLE_ADMIN"},
            "user",  new String[]{"user123",   "ROLE_USER"},
            "vikas", new String[]{"password",  "ROLE_USER"}
    );

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        String[] entry = USER_STORE.get(username.toLowerCase());

        if (entry == null) {
            throw new UsernameNotFoundException(
                    "User not found: " + username);
        }

        String password = entry[0];
        String role     = entry[1];

        /*
         * Build Spring Security UserDetails.
         * NOTE: {noop} prefix tells Spring Security to use plain-text
         *       password comparison (no encoding). In production, use
         *       BCryptPasswordEncoder.
         */
        return User.builder()
                .username(username.toLowerCase())
                .password("{noop}" + password)
                .authorities(role)
                .build();
    }
}
