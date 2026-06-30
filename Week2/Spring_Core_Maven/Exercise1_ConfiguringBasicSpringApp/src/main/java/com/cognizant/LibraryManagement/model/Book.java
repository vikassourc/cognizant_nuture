package com.cognizant.LibraryManagement.model;

/**
 * Domain model representing a Book in the Library Management System.
 *
 * <p>This is a plain Java bean (POJO) that encapsulates all attributes
 * of a library book. It is used across all layers (model, repository,
 * service) of the application.</p>
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

    /** Name of the author. */
    private String author;

    /** International Standard Book Number (ISBN-13). */
    private String isbn;

    /** Retail price of the book in INR. */
    private double price;

    /**
     * Default no-argument constructor required for Spring bean creation
     * and Java serialization.
     */
    public Book() {
    }

    /**
     * Full constructor for creating a {@code Book} with all attributes.
     *
     * @param id     unique identifier
     * @param title  title of the book
     * @param author author's full name
     * @param isbn   ISBN-13 string
     * @param price  price in INR
     */
    public Book(int id, String title, String author, String isbn, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    // ----------------------------------------------------------------
    // Getters and Setters
    // ----------------------------------------------------------------

    /**
     * Returns the book's unique identifier.
     * @return the ID
     */
    public int getId() { return id; }

    /**
     * Sets the book's unique identifier.
     * @param id the ID to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the book's title.
     * @return the title
     */
    public String getTitle() { return title; }

    /**
     * Sets the book's title.
     * @param title the title to set
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Returns the author's name.
     * @return the author
     */
    public String getAuthor() { return author; }

    /**
     * Sets the author's name.
     * @param author the author to set
     */
    public void setAuthor(String author) { this.author = author; }

    /**
     * Returns the book's ISBN.
     * @return the ISBN-13 string
     */
    public String getIsbn() { return isbn; }

    /**
     * Sets the book's ISBN.
     * @param isbn the ISBN-13 to set
     */
    public void setIsbn(String isbn) { this.isbn = isbn; }

    /**
     * Returns the book's price.
     * @return the price in INR
     */
    public double getPrice() { return price; }

    /**
     * Sets the book's price.
     * @param price the price to set (in INR)
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Returns a human-readable string representation of this book.
     *
     * @return formatted string with all book attributes
     */
    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', author='%s', isbn='%s', price=%.2f}",
                id, title, author, isbn, price);
    }
}
