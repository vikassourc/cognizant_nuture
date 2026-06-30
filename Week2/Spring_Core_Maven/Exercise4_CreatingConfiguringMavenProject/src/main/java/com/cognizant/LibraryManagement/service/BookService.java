package com.cognizant.LibraryManagement.service;

import com.cognizant.LibraryManagement.model.Book;
import java.util.List;

/**
 * Service interface for Library Book operations.
 *
 * <p>Defines the business-layer contract. The implementation
 * ({@link BookServiceImpl}) is annotated with {@code @Service} and
 * auto-detected by Spring's component scanner.</p>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public interface BookService {

    /**
     * Finds a book by its unique identifier.
     * @param id the book ID
     * @return matching Book or null
     */
    Book findBookById(int id);

    /**
     * Retrieves all books.
     * @return list of all books
     */
    List<Book> getAllBooks();

    /**
     * Adds a new book.
     * @param book the book to add
     */
    void addBook(Book book);
}
