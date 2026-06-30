package com.cognizant.LibraryManagement.service;

import com.cognizant.LibraryManagement.model.Book;
import com.cognizant.LibraryManagement.repository.BookRepository;

import java.util.List;

/**
 * Implementation of {@link BookService} using setter-based dependency injection.
 *
 * <p>This class delegates all data operations to a {@link BookRepository} instance,
 * which is injected by the Spring container via a setter method. This pattern
 * (setter injection) is configured in {@code applicationContext.xml} using
 * the {@code <property>} element.</p>
 *
 * <p><b>Setter Injection Flow:</b></p>
 * <ol>
 *   <li>Spring creates a {@code BookServiceImpl} bean using the no-arg constructor.</li>
 *   <li>Spring calls {@link #setBookRepository(BookRepository)} with the
 *       {@code bookRepository} bean as the argument.</li>
 *   <li>The service is now ready to serve requests.</li>
 * </ol>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public class BookServiceImpl implements BookService {

    /**
     * Repository dependency injected by Spring via setter injection.
     * Handles all CRUD operations on {@link Book} entities.
     */
    private BookRepository bookRepository;

    /**
     * Default no-argument constructor required by Spring for instantiation
     * when using setter injection.
     */
    public BookServiceImpl() {
        System.out.println("[SPRING] BookServiceImpl instantiated (no-arg constructor).");
    }

    /**
     * Setter method used by Spring to inject the {@link BookRepository} dependency.
     *
     * <p>This method is called by the Spring container after bean instantiation,
     * as specified by the {@code <property name='bookRepository' ref='bookRepository'/>}
     * element in {@code applicationContext.xml}.</p>
     *
     * @param bookRepository the {@link BookRepository} implementation to inject
     */
    public void setBookRepository(BookRepository bookRepository) {
        System.out.println("[SPRING] Setter injection: setBookRepository() called.");
        this.bookRepository = bookRepository;
    }

    /**
     * Returns the currently injected {@link BookRepository}.
     *
     * @return the book repository
     */
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Delegates to {@link BookRepository#findById(int)}.</p>
     */
    @Override
    public Book findBookById(int id) {
        System.out.println("[SERVICE] findBookById called with id=" + id);
        return bookRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Delegates to {@link BookRepository#findAll()}.</p>
     */
    @Override
    public List<Book> getAllBooks() {
        System.out.println("[SERVICE] getAllBooks called.");
        return bookRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Delegates to {@link BookRepository#save(Book)}.</p>
     */
    @Override
    public void addBook(Book book) {
        System.out.println("[SERVICE] addBook called for: " + book.getTitle());
        bookRepository.save(book);
    }
}
