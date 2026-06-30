package com.cognizant.LibraryManagement.repository;

import com.cognizant.LibraryManagement.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory {@link BookRepository} implementation.
 *
 * <p>{@code @Repository} serves two purposes:</p>
 * <ol>
 *   <li>Marks this class as a Spring-managed persistence component,
 *       enabling auto-detection by {@code @ComponentScan}.</li>
 *   <li>Enables Spring's exception translation mechanism, converting
 *       data-access exceptions into Spring's DataAccessException hierarchy.</li>
 * </ol>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
@Repository
public class BookRepositoryImpl implements BookRepository {

    /** In-memory list serving as the data store. */
    private final List<Book> books = new ArrayList<>();

    /**
     * Constructor initializes sample book data.
     * Called by Spring when creating this {@code @Repository} bean.
     */
    public BookRepositoryImpl() {
        System.out.println("[SPRING] @Repository BookRepositoryImpl created.");
        books.add(new Book(1, "Effective Java", "Joshua Bloch", "978-0134685991", 649.00));
        books.add(new Book(2, "Clean Code", "Robert C. Martin", "978-0132350884", 499.00));
        books.add(new Book(3, "Design Patterns", "Gang of Four", "978-0201633610", 899.00));
        books.add(new Book(4, "Java: The Complete Reference", "Herbert Schildt", "978-1260440232", 599.00));
        System.out.println("[SPRING] BookRepositoryImpl: " + books.size() + " books pre-loaded.");
    }

    /** {@inheritDoc} */
    @Override
    public Book findById(int id) {
        System.out.println("[REPOSITORY] findById id=" + id);
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    /** {@inheritDoc} */
    @Override
    public List<Book> findAll() {
        System.out.println("[REPOSITORY] findAll - " + books.size() + " books.");
        return new ArrayList<>(books);
    }

    /** {@inheritDoc} */
    @Override
    public void save(Book book) {
        System.out.println("[REPOSITORY] save: " + book.getTitle());
        books.add(book);
    }
}
