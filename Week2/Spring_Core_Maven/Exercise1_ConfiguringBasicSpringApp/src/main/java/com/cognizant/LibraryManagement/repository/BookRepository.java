package com.cognizant.LibraryManagement.repository;

import com.cognizant.LibraryManagement.model.Book;
import java.util.List;

/**
 * Repository interface defining data access operations for {@link Book} entities.
 *
 * <p>This interface abstracts the underlying data storage mechanism, following the
 * Repository pattern. The Spring container injects the appropriate implementation
 * ({@link BookRepositoryImpl}) wherever this interface is required.</p>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public interface BookRepository {

    /**
     * Finds a book by its unique identifier.
     *
     * @param id the unique ID of the book
     * @return the {@link Book} matching the given ID, or {@code null} if not found
     */
    Book findById(int id);

    /**
     * Retrieves all books from the data store.
     *
     * @return a {@link List} of all {@link Book} objects; never {@code null}
     */
    List<Book> findAll();

    /**
     * Persists a book to the data store.
     *
     * @param book the {@link Book} to save; must not be {@code null}
     */
    void save(Book book);
}
