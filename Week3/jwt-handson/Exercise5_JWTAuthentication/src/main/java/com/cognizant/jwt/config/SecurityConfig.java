package com.cognizant.jwt.config;

import com.cognizant.jwt.filter.JwtAuthenticationFilter;
import com.cognizant.jwt.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Week 3 – Exercise 5 (Day 30)
 * Task: Create authentication service that returns JWT
 *
 * Spring Security Configuration.
 *
 * Security Rules:
 *   - POST /auth/login  → PERMIT ALL (no authentication required)
 *   - ALL other routes  → REQUIRE valid JWT
 *
 * Session policy: STATELESS (no server-side sessions – JWT handles state)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Configures the security filter chain.
     * CSRF disabled since the API is stateless (JWT-based).
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for stateless REST APIs
            .csrf(csrf -> csrf.disable())

            // Configure authorisation rules
            .authorizeHttpRequests(auth -> auth
                // Public endpoints – no token required
                .requestMatchers("/auth/login", "/auth/register").permitAll()
                // All other endpoints require authentication
                .anyRequest().authenticated()
            )

            // Stateless session – Spring Security won't create HTTP sessions
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Register the JWT filter BEFORE the standard username/password filter
            .addFilterBefore(jwtAuthFilter,
                             UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /** Authentication provider – links UserDetailsService to Spring Security. */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * AuthenticationManager – required to authenticate credentials
     * in the AuthController.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * NoOpPasswordEncoder – for demo purposes only.
     * In production: use BCryptPasswordEncoder.
     */
    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
