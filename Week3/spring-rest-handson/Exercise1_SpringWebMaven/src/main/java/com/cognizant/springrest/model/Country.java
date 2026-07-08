package com.cognizant.springrest.model;

/**
 * Week 3 – Exercise 1 (Day 26)
 * Task: Spring Core – Load Country from Spring Configuration XML
 *
 * Plain Java bean (POJO) representing a Country.
 * Loaded via Spring's ApplicationContext from spring-config.xml.
 */
public class Country {

    private String code;        // ISO 3166-1 alpha-2 code, e.g. "IN"
    private String name;        // Full country name, e.g. "India"
    private String capital;     // Capital city
    private long population;    // Approximate population

    // ─── Default constructor (required by Spring XML bean instantiation) ───
    public Country() {}

    // ─── Parameterised constructor ────────────────────────────────────────
    public Country(String code, String name, String capital, long population) {
        this.code       = code;
        this.name       = name;
        this.capital    = capital;
        this.population = population;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────
    public String getCode()              { return code; }
    public void   setCode(String code)   { this.code = code; }

    public String getName()              { return name; }
    public void   setName(String name)   { this.name = name; }

    public String getCapital()                   { return capital; }
    public void   setCapital(String capital)     { this.capital = capital; }

    public long   getPopulation()                { return population; }
    public void   setPopulation(long population) { this.population = population; }

    @Override
    public String toString() {
        return "Country{code='" + code + "', name='" + name
                + "', capital='" + capital + "', population=" + population + "}";
    }
}
