package com.cognizant.jwt.filter;

import com.cognizant.jwt.service.CustomUserDetailsService;
import com.cognizant.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Week 3 – Exercise 5 (Day 30)
 * Task: Create authentication service that returns JWT
 *
 * JWT Authentication Filter – runs once per HTTP request.
 *
 * Processing steps:
 *   1. Read the Authorization header
 *   2. Extract the Bearer token
 *   3. Validate the token using JwtUtil
 *   4. Set the authentication in SecurityContext if valid
 *
 * This filter intercepts every request and authenticates users
 * based on a valid JWT, enabling stateless authentication.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest  request,
                                    HttpServletResponse response,
                                    FilterChain         filterChain)
            throws ServletException, IOException {

        // ── Step 1: Read Authorization header ─────────────────────────────
        final String authHeader = request.getHeader("Authorization");

        // If there is no Bearer token, skip this filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ── Step 2: Extract token (remove "Bearer " prefix) ───────────────
        final String jwt      = authHeader.substring(7);
        final String username;

        try {
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            // Malformed or invalid token – skip authentication
            filterChain.doFilter(request, response);
            return;
        }

        // ── Step 3: Validate token and set security context ───────────────
        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // ── Step 4: Register authentication in SecurityContext ─────
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
