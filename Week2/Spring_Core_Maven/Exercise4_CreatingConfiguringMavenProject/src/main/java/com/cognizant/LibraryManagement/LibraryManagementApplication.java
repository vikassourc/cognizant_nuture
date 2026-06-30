package com.cognizant.LibraryManagement;

import com.cognizant.LibraryManagement.config.AppConfig;
import com.cognizant.LibraryManagement.model.Book;
import com.cognizant.LibraryManagement.service.BookService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Main application class demonstrating annotation-based Spring configuration.
 *
 * <p>This exercise uses {@link AnnotationConfigApplicationContext} instead of
 * {@link org.springframework.context.support.ClassPathXmlApplicationContext},
 * loading configuration from a Java {@link AppConfig} class rather than XML.</p>
 *
 * <p><b>Annotation-based Configuration Benefits:</b></p>
 * <ul>
 *   <li>Type-safe: Compiler catches typos unlike XML strings.</li>
 *   <li>Refactor-friendly: IDE renames propagate automatically.</li>
 *   <li>Testable: Configuration classes are plain Java, easy to unit test.</li>
 * </ul>
 *
 * <p><b>Annotations Used:</b></p>
 * <ul>
 *   <li>{@code @Configuration} - on {@link AppConfig}</li>
 *   <li>{@code @ComponentScan} - on {@link AppConfig}</li>
 *   <li>{@code @Service} - on {@code BookServiceImpl}</li>
 *   <li>{@code @Repository} - on {@code BookRepositoryImpl}</li>
 *   <li>{@code @Autowired} - on constructor in {@code BookServiceImpl}</li>
 * </ul>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public class LibraryManagementApplication {

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println(" Library Management - Annotation-Based Spring Config");
        System.out.println("============================================================");

        // Initialize AnnotationConfigApplicationContext with the @Configuration class.
        // Spring scans com.cognizant package and registers all annotated beans.
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n[INFO] AnnotationConfigApplicationContext loaded.");

        // Retrieve the application description bean defined via @Bean in AppConfig
        String description = context.getBean("applicationDescription", String.class);
        System.out.println("[INFO] App: " + description);

        // Retrieve BookService - discovered by @ComponentScan via @Service annotation
        BookService bookService = context.getBean(BookService.class);
        System.out.println("[INFO] BookService bean retrieved via @Service + @ComponentScan.");

        // ----------------------------------------------------------------
        // Demonstrate: Get All Books
        // ----------------------------------------------------------------
        System.out.println("\n--- All Books (from @Repository) ---");
        List<Book> allBooks = bookService.getAllBooks();
        allBooks.forEach(b -> System.out.println("  " + b));

        // ----------------------------------------------------------------
        // Demonstrate: Find Book By ID
        // ----------------------------------------------------------------
        System.out.println("\n--- Find Book by ID = 2 ---");
        Book found = bookService.findBookById(2);
        System.out.println("  " + (found != null ? found : "Not found"));

        // ----------------------------------------------------------------
        // Demonstrate: Add Book
        // ----------------------------------------------------------------
        System.out.println("\n--- Adding New Book ---");
        Book newBook = new Book(5, "Spring in Action", "Craig Walls", "978-1617294945", 799.00);
        bookService.addBook(newBook);
        System.out.println("  Added: " + newBook);

        // ----------------------------------------------------------------
        // Verify addition
        // ----------------------------------------------------------------
        System.out.println("\n--- Updated Book List ---");
        bookService.getAllBooks().forEach(b -> System.out.println("  " + b));

        context.close();
        System.out.println("\n[INFO] ApplicationContext closed.");
        System.out.println("============================================================");
    }
}
