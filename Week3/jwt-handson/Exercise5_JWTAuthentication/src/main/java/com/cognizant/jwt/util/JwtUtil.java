package com.cognizant.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Week 3 – Exercise 5 (Day 30)
 * Task: Create authentication service that returns JWT
 *
 * Utility class responsible for:
 *   1. Generating a signed JWT token from a username
 *   2. Extracting claims (username, expiry, etc.) from a token
 *   3. Validating a token against a UserDetails object
 *
 * JWT Structure:
 *   Header.Payload.Signature
 *   - Header:    { "alg": "HS256", "typ": "JWT" }
 *   - Payload:   { "sub": username, "iat": issuedAt, "exp": expiry, ... }
 *   - Signature: HMAC-SHA256 signed using the secret key
 */
@Component
public class JwtUtil {

    /** Secret key (Base64-encoded, 256-bit minimum for HS256). */
    @Value("${jwt.secret:Y29nbml6YW50TnVydHVyZVNlY3JldEtleVdlZWszMjAyNkpXVEF1dGg=}")
    private String secretKeyBase64;

    /** Token validity: 10 hours in milliseconds. */
    @Value("${jwt.expiration:36000000}")
    private long jwtExpirationMs;

    // ─── Key helpers ─────────────────────────────────────────────────────

    /** Decodes the Base64 secret and returns a SecretKey for HMAC-SHA256. */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyBase64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ─── Token Generation ─────────────────────────────────────────────────

    /**
     * Generates a JWT token for the given username.
     * Claims added:
     *   - sub  : username (subject)
     *   - iat  : issued-at timestamp
     *   - exp  : expiry timestamp
     *   - roles: user roles (demonstration)
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", "ROLE_USER");   // Extra claim – role information
        return buildToken(claims, username);
    }

    private String buildToken(Map<String, Object> extraClaims, String subject) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    // ─── Token Parsing ────────────────────────────────────────────────────

    /** Extracts the username (subject) from a JWT token. */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /** Extracts the expiry date from a JWT token. */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /** Extracts a specific claim using a resolver function. */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ─── Token Validation ─────────────────────────────────────────────────

    /**
     * Validates the token against the authenticated UserDetails.
     * Checks:
     *   1. Username in token matches the UserDetails username
     *   2. Token has not expired
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /** Returns the configured expiration duration in milliseconds. */
    public long getExpirationMs() {
        return jwtExpirationMs;
    }
}
