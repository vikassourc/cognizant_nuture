package com.cognizant.LibraryManagement.service;

import com.cognizant.LibraryManagement.model.Book;
import java.util.List;

/**
 * Service interface for Library Book operations.
 *
 * <p>Defines the contract for the business layer of the Library Management System.
 * Implementations of this interface are managed by the Spring IoC container and
 * are injected with a {@link com.cognizant.LibraryManagement.repository.BookRepository}
 * dependency at runtime.</p>
 *
 * <p>This interface follows the <em>programming to interfaces</em> principle,
 * which promotes loose coupling and testability.</p>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public interface BookService {

    /**
     * Finds a book by its unique identifier.
     *
     * @param id the unique integer ID of the book
     * @return the {@link Book} with the given ID, or {@code null} if not found
     */
    Book findBookById(int id);

    /**
     * Retrieves all books currently in the library.
     *
     * @return a {@link List} of all {@link Book} objects; never {@code null}
     */
    List<Book> getAllBooks();

    /**
     * Adds a new book to the library.
     *
     * @param book the {@link Book} to add; must not be {@code null}
     */
    void addBook(Book book);
}
