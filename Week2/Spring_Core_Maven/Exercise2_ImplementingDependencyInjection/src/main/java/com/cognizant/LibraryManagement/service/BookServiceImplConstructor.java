package com.cognizant.LibraryManagement.service;

import com.cognizant.LibraryManagement.model.Book;
import com.cognizant.LibraryManagement.repository.BookRepository;

import java.util.List;

/**
 * {@link BookService} implementation using <strong>Constructor Injection</strong>.
 *
 * <p>In constructor injection, all required dependencies are declared as constructor
 * parameters. The Spring container resolves and passes the appropriate bean instances
 * when creating this bean. This is the <em>recommended</em> injection style in Spring
 * for mandatory dependencies because:</p>
 * <ul>
 *   <li>It guarantees the object is fully initialized at creation time.</li>
 *   <li>The dependency can be declared {@code final} (immutable reference).</li>
 *   <li>It facilitates unit testing (can pass mocks directly to constructor).</li>
 * </ul>
 *
 * <p><b>XML Configuration:</b></p>
 * <pre>
 * &lt;bean id="bookServiceConstructor"
 *       class="...BookServiceImplConstructor"&gt;
 *     &lt;constructor-arg ref="bookRepository"/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public class BookServiceImplConstructor implements BookService {

    /**
     * Mandatory dependency - injected via constructor.
     * Declared final to reinforce immutability after injection.
     */
    private final BookRepository bookRepository;

    /**
     * Constructor used by Spring for dependency injection.
     *
     * <p>Spring calls this constructor and passes the {@code bookRepository} bean
     * as specified by {@code <constructor-arg ref="bookRepository"/>} in
     * {@code applicationContext.xml}.</p>
     *
     * @param bookRepository the {@link BookRepository} to inject; must not be null
     */
    public BookServiceImplConstructor(BookRepository bookRepository) {
        System.out.println("[SPRING] BookServiceImplConstructor: constructor injection called.");
        this.bookRepository = bookRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book findBookById(int id) {
        System.out.println("[CONSTRUCTOR-SERVICE] findBookById id=" + id);
        return bookRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> getAllBooks() {
        System.out.println("[CONSTRUCTOR-SERVICE] getAllBooks");
        return bookRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBook(Book book) {
        System.out.println("[CONSTRUCTOR-SERVICE] addBook: " + book.getTitle());
        bookRepository.save(book);
    }
}
