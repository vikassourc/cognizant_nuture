package com.cognizant.springrest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Week 3 – Exercise 2 (Day 27)
 * Task: Hello World RESTful Web Service
 *
 * Exposes a simple REST endpoint that returns a greeting.
 * Demonstrates:
 *   - @RestController (combines @Controller + @ResponseBody)
 *   - @GetMapping
 *   - @PathVariable
 *   - @RequestParam
 *   - Returning JSON via ResponseEntity
 */
@RestController
@RequestMapping("/api")
public class HelloWorldController {

    /**
     * GET /api/hello
     * Returns a basic "Hello, World!" JSON message.
     */
    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message",   "Hello, World!");
        response.put("status",    "success");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/hello/{name}
     * Returns a personalised greeting using a path variable.
     * Example: GET /api/hello/Vikas → "Hello, Vikas!"
     */
    @GetMapping("/hello/{name}")
    public ResponseEntity<Map<String, Object>> helloName(
            @PathVariable String name) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message",   "Hello, " + name + "!");
        response.put("status",    "success");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/greet?name=Vikas&lang=en
     * Returns a greeting based on optional request parameters.
     */
    @GetMapping("/greet")
    public ResponseEntity<Map<String, Object>> greet(
            @RequestParam(defaultValue = "World") String name,
            @RequestParam(defaultValue = "en")    String lang) {

        String greeting = switch (lang.toLowerCase()) {
            case "hi" -> "नमस्ते, " + name + "!";
            case "fr" -> "Bonjour, " + name + "!";
            case "es" -> "¡Hola, " + name + "!";
            case "de" -> "Hallo, " + name + "!";
            default   -> "Hello, " + name + "!";
        };

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message",   greeting);
        response.put("language",  lang);
        response.put("status",    "success");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
}
