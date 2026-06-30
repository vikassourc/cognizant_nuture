package com.cognizant.LibraryManagement.repository;

import com.cognizant.LibraryManagement.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of {@link BookRepository}.
 *
 * <p>Uses an {@link ArrayList} as a simple in-memory data store. In a production
 * application, this would be replaced with a database-backed implementation
 * (e.g., using JPA/Hibernate or Spring Data JPA).</p>
 *
 * <p>This bean is instantiated and managed by the Spring IoC container.
 * It is pre-populated with sample books in the constructor to simulate
 * an existing library catalog.</p>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public class BookRepositoryImpl implements BookRepository {

    /**
     * In-memory list acting as the data store for books.
     */
    private final List<Book> books = new ArrayList<>();

    /**
     * Constructor that initializes the repository with sample book data.
     *
     * <p>Called by the Spring container when creating the {@code bookRepository} bean.
     * Pre-loads 4 sample books to demonstrate the application.</p>
     */
    public BookRepositoryImpl() {
        System.out.println("[SPRING] BookRepositoryImpl instantiated - loading sample data.");
        // Initialize with sample books
        books.add(new Book(1, "Effective Java", "Joshua Bloch", "978-0134685991", 649.00));
        books.add(new Book(2, "Clean Code", "Robert C. Martin", "978-0132350884", 499.00));
        books.add(new Book(3, "Design Patterns", "Gang of Four", "978-0201633610", 899.00));
        books.add(new Book(4, "Java: The Complete Reference", "Herbert Schildt", "978-1260440232", 599.00));
        System.out.println("[SPRING] BookRepositoryImpl: " + books.size() + " sample books loaded.");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Performs a linear search through the in-memory list.</p>
     *
     * @param id the ID to search for
     * @return the matching {@link Book}, or {@code null} if not found
     */
    @Override
    public Book findById(int id) {
        System.out.println("[REPOSITORY] findById called with id=" + id);
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     *
     * @return a copy of all books in the repository
     */
    @Override
    public List<Book> findAll() {
        System.out.println("[REPOSITORY] findAll called - returning " + books.size() + " books.");
        return new ArrayList<>(books);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Adds the book to the in-memory list.</p>
     *
     * @param book the book to save
     */
    @Override
    public void save(Book book) {
        System.out.println("[REPOSITORY] save called for: " + book.getTitle());
        books.add(book);
    }
}
