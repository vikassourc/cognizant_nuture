package com.cognizant.LibraryManagement.repository;

import com.cognizant.LibraryManagement.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of {@link BookRepository} for Exercise 2.
 *
 * <p>Shared by both {@code BookServiceImplConstructor} and
 * {@code BookServiceImplSetter} to demonstrate that the same repository
 * bean can be injected in different ways.</p>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public class BookRepositoryImpl implements BookRepository {

    /** In-memory list of books. */
    private final List<Book> books = new ArrayList<>();

    /**
     * Initializes the repository with sample data.
     */
    public BookRepositoryImpl() {
        System.out.println("[SPRING] BookRepositoryImpl (Ex2) instantiated.");
        books.add(new Book(1, "Effective Java", "Joshua Bloch", "978-0134685991", 649.00));
        books.add(new Book(2, "Clean Code", "Robert C. Martin", "978-0132350884", 499.00));
        books.add(new Book(3, "Design Patterns", "Gang of Four", "978-0201633610", 899.00));
        books.add(new Book(4, "Java: The Complete Reference", "Herbert Schildt", "978-1260440232", 599.00));
        System.out.println("[SPRING] BookRepositoryImpl: " + books.size() + " books loaded.");
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
