package com.cognizant.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Week 3 – Exercise 5 (Day 30)
 *
 * Protected REST Controller – requires a valid JWT to access.
 * Demonstrates JWT-protected endpoints.
 *
 * All endpoints require: Authorization: Bearer <token>
 */
@RestController
@RequestMapping("/api/secure")
public class SecureController {

    /**
     * GET /api/secure/hello
     * Simple protected endpoint – returns a greeting for the authenticated user.
     */
    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(Map.of(
                "message",   "Hello, " + auth.getName() + "! You are authenticated.",
                "username",  auth.getName(),
                "roles",     auth.getAuthorities().toString(),
                "timestamp", LocalDateTime.now().toString()
        ));
    }

    /**
     * GET /api/secure/profile
     * Returns the authenticated user's profile information extracted from JWT.
     */
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> profile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(Map.of(
                "username",    auth.getName(),
                "authorities", auth.getAuthorities().toString(),
                "authenticated", auth.isAuthenticated(),
                "message",     "Profile data retrieved successfully via JWT."
        ));
    }

    /**
     * GET /api/secure/dashboard
     * Simulated dashboard endpoint – only accessible with a valid JWT.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(Map.of(
                "status",    "success",
                "user",      auth.getName(),
                "message",   "Welcome to your secure dashboard!",
                "hint",      "This endpoint is protected by JWT. "
                           + "You must pass a valid Bearer token to access it.",
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}
