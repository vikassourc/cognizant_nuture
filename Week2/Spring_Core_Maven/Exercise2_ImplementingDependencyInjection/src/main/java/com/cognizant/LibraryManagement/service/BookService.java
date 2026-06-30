package com.cognizant.LibraryManagement.service;

import com.cognizant.LibraryManagement.model.Book;
import java.util.List;

/**
 * Service interface for Library Book operations.
 *
 * <p>Both {@link BookServiceImplConstructor} and {@link BookServiceImplSetter}
 * implement this interface, demonstrating that different injection strategies
 * can back the same contract.</p>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public interface BookService {

    /**
     * Finds a book by its unique identifier.
     *
     * @param id the unique integer ID
     * @return the matching {@link Book}, or {@code null} if not found
     */
    Book findBookById(int id);

    /**
     * Retrieves all books in the library.
     *
     * @return list of all books; never {@code null}
     */
    List<Book> getAllBooks();

    /**
     * Adds a book to the library.
     *
     * @param book the book to add
     */
    void addBook(Book book);
}
