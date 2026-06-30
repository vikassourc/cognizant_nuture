package com.cognizant.LibraryManagement.repository;

import com.cognizant.LibraryManagement.model.Book;
import java.util.List;

/**
 * Repository interface defining data access operations.
 *
 * <p>The implementation ({@link BookRepositoryImpl}) is annotated with
 * {@code @Repository} and discovered by Spring's component scanner.</p>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public interface BookRepository {

    /**
     * Finds a book by ID.
     * @param id the book ID
     * @return matching Book or null
     */
    Book findById(int id);

    /**
     * Returns all books.
     * @return list of books
     */
    List<Book> findAll();

    /**
     * Saves a book.
     * @param book the book to persist
     */
    void save(Book book);
}
