package com.cognizant.LibraryManagement.service;

import com.cognizant.LibraryManagement.model.Book;
import com.cognizant.LibraryManagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link BookService} implementation using Spring annotation-based configuration.
 *
 * <p>This class is auto-registered as a Spring bean through two mechanisms:</p>
 * <ol>
 *   <li>{@code @Service} annotation marks it as a Spring-managed service component.</li>
 *   <li>{@code @ComponentScan} in {@code AppConfig} instructs Spring to discover it.</li>
 * </ol>
 *
 * <p>{@code @Autowired} on the constructor tells Spring to inject a
 * {@link BookRepository} bean automatically. Constructor injection is preferred
 * over field injection for testability and immutability.</p>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
@Service
public class BookServiceImpl implements BookService {

    /**
     * Repository dependency auto-injected by Spring via constructor.
     */
    private final BookRepository bookRepository;

    /**
     * Constructor injection with {@code @Autowired}.
     *
     * <p>Spring automatically finds a bean implementing {@link BookRepository}
     * (i.e., {@code BookRepositoryImpl} annotated with {@code @Repository})
     * and injects it here. No XML configuration is needed.</p>
     *
     * @param bookRepository the repository bean to inject
     */
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        System.out.println("[SPRING] @Service BookServiceImpl created with @Autowired constructor.");
        this.bookRepository = bookRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book findBookById(int id) {
        System.out.println("[SERVICE] findBookById id=" + id);
        return bookRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> getAllBooks() {
        System.out.println("[SERVICE] getAllBooks");
        return bookRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBook(Book book) {
        System.out.println("[SERVICE] addBook: " + book.getTitle());
        bookRepository.save(book);
    }
}
