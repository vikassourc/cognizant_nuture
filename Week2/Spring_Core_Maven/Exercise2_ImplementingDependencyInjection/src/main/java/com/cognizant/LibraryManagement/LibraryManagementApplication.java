package com.cognizant.LibraryManagement;

import com.cognizant.LibraryManagement.model.Book;
import com.cognizant.LibraryManagement.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Main application class demonstrating both Constructor and Setter Dependency Injection.
 *
 * <p>Spring supports multiple ways to inject dependencies into beans. This exercise
 * demonstrates two primary approaches:</p>
 *
 * <ul>
 *   <li><b>Constructor Injection:</b> Dependencies are provided as constructor parameters.
 *       Use when the dependency is mandatory and the object should be immutable
 *       after creation.</li>
 *   <li><b>Setter Injection:</b> Dependencies are injected via setter methods after
 *       instantiation. Use when the dependency is optional or when you need to
 *       change it at runtime.</li>
 * </ul>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
public class LibraryManagementApplication {

    /**
     * Application entry point - demonstrates both DI styles.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println(" Library Management - Dependency Injection Demo");
        System.out.println("============================================================");

        // Load the Spring ApplicationContext from XML
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n[INFO] Spring ApplicationContext initialized.");

        // ================================================================
        // DEMO 1: Constructor Injection
        // Bean 'bookServiceConstructor' uses BookServiceImplConstructor
        // which receives BookRepository via its constructor.
        // ================================================================
        System.out.println("\n====== CONSTRUCTOR INJECTION DEMO ======");
        BookService constructorService = (BookService) context.getBean("bookServiceConstructor");
        System.out.println("Bean retrieved: bookServiceConstructor (Constructor Injection)");

        System.out.println("\n[Constructor DI] All books:");
        List<Book> books1 = constructorService.getAllBooks();
        books1.forEach(b -> System.out.println("  " + b));

        System.out.println("\n[Constructor DI] Find book with ID=2:");
        Book b1 = constructorService.findBookById(2);
        System.out.println("  Found: " + (b1 != null ? b1 : "Not found"));

        System.out.println("\n[Constructor DI] Adding a new book:");
        Book newBook1 = new Book(5, "Head First Java", "Kathy Sierra", "978-0596009205", 549.00);
        constructorService.addBook(newBook1);
        System.out.println("  Added: " + newBook1);

        // ================================================================
        // DEMO 2: Setter Injection
        // Bean 'bookServiceSetter' uses BookServiceImplSetter
        // which receives BookRepository via a setter method.
        // ================================================================
        System.out.println("\n====== SETTER INJECTION DEMO ======");
        BookService setterService = (BookService) context.getBean("bookServiceSetter");
        System.out.println("Bean retrieved: bookServiceSetter (Setter Injection)");

        System.out.println("\n[Setter DI] All books:");
        List<Book> books2 = setterService.getAllBooks();
        books2.forEach(b -> System.out.println("  " + b));

        System.out.println("\n[Setter DI] Find book with ID=3:");
        Book b2 = setterService.findBookById(3);
        System.out.println("  Found: " + (b2 != null ? b2 : "Not found"));

        System.out.println("\n[Setter DI] Adding a new book:");
        Book newBook2 = new Book(6, "Refactoring", "Martin Fowler", "978-0201485677", 699.00);
        setterService.addBook(newBook2);
        System.out.println("  Added: " + newBook2);

        // Close the context
        ((ClassPathXmlApplicationContext) context).close();
        System.out.println("\n[INFO] Spring context closed.");
        System.out.println("============================================================");
    }
}
