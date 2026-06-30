package com.cognizant.LibraryManagement.service;

import com.cognizant.LibraryManagement.model.Book;
import com.cognizant.LibraryManagement.repository.BookRepository;

import java.util.List;

/**
 * {@link BookService} implementation using <strong>Setter Injection</strong>.
 *
 * <p>In setter injection, Spring first calls the no-argument constructor to
 * instantiate the bean, then calls the setter method(s) to inject dependencies.
 * This approach is useful when:</p>
 * <ul>
 *   <li>The dependency is optional.</li>
 *   <li>You need to re-inject or change the dependency at runtime.</li>
 *   <li>There are circular dependencies that prevent constructor injection.</li>
 * </ul>
 *
 * <p><b>XML Configuration:</b></p>
 * <pre>
 * &lt;bean id="bookServiceSetter"
 *       class="...BookServiceImplSetter"&gt;
 *     &lt;property name="bookRepository" ref="bookRepository"/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public class BookServiceImplSetter implements BookService {

    /**
     * Dependency injected by Spring via the {@link #setBookRepository(BookRepository)} method.
     */
    private BookRepository bookRepository;

    /**
     * No-argument constructor required by Spring for setter injection.
     * Spring instantiates the bean using this constructor before calling setters.
     */
    public BookServiceImplSetter() {
        System.out.println("[SPRING] BookServiceImplSetter: no-arg constructor called.");
    }

    /**
     * Setter method used by Spring to inject the {@link BookRepository} dependency.
     *
     * <p>Corresponds to {@code <property name="bookRepository" ref="bookRepository"/>}
     * in {@code applicationContext.xml}. Spring derives the property name by
     * stripping the 'set' prefix and lowercasing the first letter.</p>
     *
     * @param bookRepository the repository to inject
     */
    public void setBookRepository(BookRepository bookRepository) {
        System.out.println("[SPRING] BookServiceImplSetter: setBookRepository() called.");
        this.bookRepository = bookRepository;
    }

    /**
     * Returns the injected repository.
     * @return the book repository
     */
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book findBookById(int id) {
        System.out.println("[SETTER-SERVICE] findBookById id=" + id);
        return bookRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> getAllBooks() {
        System.out.println("[SETTER-SERVICE] getAllBooks");
        return bookRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBook(Book book) {
        System.out.println("[SETTER-SERVICE] addBook: " + book.getTitle());
        bookRepository.save(book);
    }
}
