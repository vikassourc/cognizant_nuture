package com.cognizant.springrest.controller;

import com.cognizant.springrest.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Week 3 – Exercise 2 (Day 28 & 29)
 * Task: REST - Country Web Service  +  Get country based on country code
 *
 * Full CRUD-style REST controller for Country resources.
 * Demonstrates:
 *   - @RestController, @RequestMapping
 *   - @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
 *   - @PathVariable, @RequestBody
 *   - Returning ResponseEntity with HTTP status codes
 *   - Injecting XML-loaded beans via @Autowired + @Qualifier
 */
@RestController
@RequestMapping("/api/country")
public class CountryController {

    /** Injected from SpringXmlLoader – loaded from spring-config.xml */
    private final List<Country> countryList;

    @Autowired
    public CountryController(
            @Qualifier("countryList") List<Country> countryList) {
        this.countryList = countryList;
    }

    // ─────────────────────────────────────────────────────────────────────
    //  Day 28 – REST Country Web Service
    // ─────────────────────────────────────────────────────────────────────

    /**
     * GET /api/country/all
     * Returns the complete list of countries.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryList);
    }

    /**
     * POST /api/country
     * Adds a new country to the in-memory list.
     * Request Body (JSON):
     * {
     *   "code": "JP",
     *   "name": "Japan",
     *   "capital": "Tokyo",
     *   "population": 125700000
     * }
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> addCountry(
            @RequestBody Country country) {

        // Check for duplicates
        boolean exists = countryList.stream()
                .anyMatch(c -> c.getCode().equalsIgnoreCase(country.getCode()));
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                        "status",  "error",
                        "message", "Country with code '" + country.getCode() + "' already exists."));
        }

        countryList.add(country);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                    "status",  "created",
                    "message", "Country '" + country.getName() + "' added successfully."));
    }

    /**
     * PUT /api/country/{code}
     * Updates an existing country identified by its ISO code.
     */
    @PutMapping("/{code}")
    public ResponseEntity<?> updateCountry(
            @PathVariable String code,
            @RequestBody  Country updated) {

        for (Country c : countryList) {
            if (c.getCode().equalsIgnoreCase(code)) {
                c.setName(updated.getName());
                c.setCapital(updated.getCapital());
                c.setPopulation(updated.getPopulation());
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("status", "error",
                             "message", "Country with code '" + code + "' not found."));
    }

    /**
     * DELETE /api/country/{code}
     * Removes a country by its ISO code.
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Map<String, String>> deleteCountry(
            @PathVariable String code) {

        boolean removed = countryList.removeIf(
                c -> c.getCode().equalsIgnoreCase(code));

        if (removed) {
            return ResponseEntity.ok(
                    Map.of("status", "deleted",
                           "message", "Country '" + code.toUpperCase() + "' removed successfully."));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("status", "error",
                             "message", "Country with code '" + code + "' not found."));
    }

    // ─────────────────────────────────────────────────────────────────────
    //  Day 29 – Get country based on country code
    // ─────────────────────────────────────────────────────────────────────

    /**
     * GET /api/country/{code}
     * Retrieves a single country by its ISO 3166-1 alpha-2 code.
     * Example: GET /api/country/IN  →  returns India
     */
    @GetMapping("/{code}")
    public ResponseEntity<?> getCountryByCode(@PathVariable String code) {

        return countryList.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "status",  "error",
                            "message", "Country with code '" + code.toUpperCase()
                                       + "' not found.",
                            "hint",    "Available codes: IN, US, GB, AU, CA")));
    }
}
