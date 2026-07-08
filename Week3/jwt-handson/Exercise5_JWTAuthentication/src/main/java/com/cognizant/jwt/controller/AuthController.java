package com.cognizant.jwt.controller;

import com.cognizant.jwt.model.AuthRequest;
import com.cognizant.jwt.model.AuthResponse;
import com.cognizant.jwt.service.CustomUserDetailsService;
import com.cognizant.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Week 3 – Exercise 5 (Day 30)
 * Task: Create authentication service that returns JWT
 *
 * Authentication Controller – handles login and token generation.
 *
 * Endpoint: POST /auth/login
 * Request Body:
 * {
 *   "username": "admin",
 *   "password": "admin123"
 * }
 *
 * Response (200 OK):
 * {
 *   "token": "eyJhbGciOiJIUzI1NiJ9...",
 *   "tokenType": "Bearer",
 *   "expiresInMs": 36000000,
 *   "username": "admin",
 *   "message": "Authentication successful..."
 * }
 *
 * Response (401 Unauthorized):
 * {
 *   "status": "error",
 *   "message": "Invalid username or password."
 * }
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * POST /auth/login
     * Authenticates the user and returns a signed JWT token.
     *
     * Steps:
     *   1. Authenticate credentials via Spring Security AuthenticationManager
     *   2. Load UserDetails for the authenticated user
     *   3. Generate a JWT token using JwtUtil
     *   4. Return the token wrapped in AuthResponse
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Step 1 – Authenticate credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            // Step 1 failed – invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                        "status",  "error",
                        "message", "Invalid username or password."
                    ));
        }

        // Step 2 – Load user details
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getUsername());

        // Step 3 – Generate JWT
        String token = jwtUtil.generateToken(userDetails.getUsername());

        // Step 4 – Build and return the response
        AuthResponse response = new AuthResponse(
                token,
                jwtUtil.getExpirationMs(),
                userDetails.getUsername()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * GET /auth/validate
     * Validates a token passed in the Authorization header.
     * Returns token details if valid.
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validate(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error",
                                 "message", "Missing or malformed Authorization header."));
        }

        String token    = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        UserDetails ud  = userDetailsService.loadUserByUsername(username);

        if (jwtUtil.isTokenValid(token, ud)) {
            return ResponseEntity.ok(Map.of(
                    "status",    "valid",
                    "username",  username,
                    "expiresAt", jwtUtil.extractExpiration(token).toString()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("status", "error", "message", "Token is invalid or expired."));
    }
}
