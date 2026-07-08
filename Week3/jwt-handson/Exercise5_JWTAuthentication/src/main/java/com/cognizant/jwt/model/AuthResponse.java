package com.cognizant.jwt.model;

/**
 * Week 3 – Exercise 5 (Day 30)
 *
 * Response payload returned by /auth/login on successful authentication.
 * Contains the signed JWT token and metadata.
 */
public class AuthResponse {

    private String  token;
    private String  tokenType = "Bearer";
    private long    expiresInMs;
    private String  username;
    private String  message;

    public AuthResponse() {}

    public AuthResponse(String token, long expiresInMs, String username) {
        this.token       = token;
        this.expiresInMs = expiresInMs;
        this.username    = username;
        this.message     = "Authentication successful. Include this token in the "
                         + "Authorization header as: Bearer <token>";
    }

    public String getToken()           { return token; }
    public void   setToken(String t)   { this.token = t; }

    public String getTokenType()             { return tokenType; }
    public void   setTokenType(String tt)    { this.tokenType = tt; }

    public long   getExpiresInMs()           { return expiresInMs; }
    public void   setExpiresInMs(long e)     { this.expiresInMs = e; }

    public String getUsername()              { return username; }
    public void   setUsername(String u)      { this.username = u; }

    public String getMessage()               { return message; }
    public void   setMessage(String m)       { this.message = m; }
}
