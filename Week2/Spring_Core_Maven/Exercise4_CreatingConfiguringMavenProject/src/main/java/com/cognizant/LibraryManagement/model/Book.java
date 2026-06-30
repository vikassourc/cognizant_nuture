package com.cognizant.LibraryManagement.model;

/**
 * Domain model representing a Book in the Library Management System.
 *
 * <p>A simple POJO (Plain Old Java Object) with all book attributes.
 * Used across all layers of the application.</p>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public class Book {

    /** Unique identifier for the book. */
    private int id;

    /** Title of the book. */
    private String title;

    /** Full name of the author. */
    private String author;

    /** ISBN-13 identifier. */
    private String isbn;

    /** Retail price in INR. */
    private double price;

    /** Default no-arg constructor for Spring and serialization. */
    public Book() {}

    /**
     * Full constructor.
     * @param id     unique ID
     * @param title  book title
     * @param author author name
     * @param isbn   ISBN-13
     * @param price  price in INR
     */
    public Book(int id, String title, String author, String isbn, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', author='%s', isbn='%s', price=%.2f}",
                id, title, author, isbn, price);
    }
}
