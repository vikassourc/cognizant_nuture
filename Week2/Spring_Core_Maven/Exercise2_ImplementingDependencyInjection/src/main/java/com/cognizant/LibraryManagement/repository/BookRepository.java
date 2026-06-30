package com.cognizant.LibraryManagement.repository;

import com.cognizant.LibraryManagement.model.Book;
import java.util.List;

/**
 * Repository interface defining data access operations for {@link Book} entities.
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public interface BookRepository {

    /**
     * Finds a book by its unique identifier.
     * @param id the book ID
     * @return the matching book or null
     */
    Book findById(int id);

    /**
     * Returns all books.
     * @return list of books
     */
    List<Book> findAll();

    /**
     * Saves a book.
     * @param book the book to save
     */
    void save(Book book);
}
