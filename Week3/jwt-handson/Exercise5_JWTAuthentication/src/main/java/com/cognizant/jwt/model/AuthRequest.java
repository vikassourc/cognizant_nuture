package com.cognizant.jwt.model;

/**
 * Week 3 – Exercise 5 (Day 30)
 *
 * Request payload sent by the client to /auth/login.
 */
public class AuthRequest {

    private String username;
    private String password;

    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername()              { return username; }
    public void   setUsername(String u)      { this.username = u; }

    public String getPassword()              { return password; }
    public void   setPassword(String p)      { this.password = p; }
}
