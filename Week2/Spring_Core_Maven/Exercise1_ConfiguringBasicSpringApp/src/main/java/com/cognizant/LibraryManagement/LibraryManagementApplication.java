package com.cognizant.LibraryManagement;

import com.cognizant.LibraryManagement.model.Book;
import com.cognizant.LibraryManagement.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Main entry point for the Library Management Application.
 *
 * <p>This class demonstrates the use of the Spring Framework's IoC container
 * by loading an {@link ApplicationContext} from an XML configuration file.
 * It retrieves Spring-managed beans and invokes business methods to show
 * how dependency injection wires components together automatically.</p>
 *
 * <p><b>Spring Concepts Demonstrated:</b></p>
 * <ul>
 *   <li>Loading ApplicationContext from XML (ClassPathXmlApplicationContext)</li>
 *   <li>Retrieving beans by name from the container</li>
 *   <li>Setter-based dependency injection</li>
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
     * <p>Bootstraps the Spring ApplicationContext from {@code applicationContext.xml},
     * retrieves the {@code bookService} bean, and demonstrates core operations:
     * listing all books, finding by ID, and adding a new book.</p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println(" Library Management System - Spring Basic Configuration");
        System.out.println("============================================================");

        // Step 1: Load the Spring ApplicationContext from XML configuration file.
        // ClassPathXmlApplicationContext searches the classpath (src/main/resources)
        // for the given XML file and initializes all defined beans.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n[INFO] Spring ApplicationContext loaded successfully.");

        // Step 2: Retrieve the 'bookService' bean from the container.
        // Spring has already injected BookRepositoryImpl into BookServiceImpl
        // via setter injection as configured in applicationContext.xml.
        BookService bookService = (BookService) context.getBean("bookService");

        System.out.println("[INFO] Retrieved 'bookService' bean from Spring container.");

        // ----------------------------------------------------------------
        // Demonstrate: Get All Books
        // ----------------------------------------------------------------
        System.out.println("\n--- All Books in Library ---");
        List<Book> books = bookService.getAllBooks();
        books.forEach(System.out::println);

        // ----------------------------------------------------------------
        // Demonstrate: Find Book By ID
        // ----------------------------------------------------------------
        System.out.println("\n--- Find Book by ID = 1 ---");
        Book found = bookService.findBookById(1);
        System.out.println(found != null ? found : "Book not found.");

        // ----------------------------------------------------------------
        // Demonstrate: Add a New Book
        // ----------------------------------------------------------------
        System.out.println("\n--- Adding a New Book ---");
        Book newBook = new Book(5, "Spring in Action", "Craig Walls", "978-1617294945", 799.00);
        bookService.addBook(newBook);
        System.out.println("Book added: " + newBook);

        // ----------------------------------------------------------------
        // Verify the new book was added
        // ----------------------------------------------------------------
        System.out.println("\n--- Updated Book List ---");
        bookService.getAllBooks().forEach(System.out::println);

        // Step 3: Close the context to release resources.
        ((ClassPathXmlApplicationContext) context).close();
        System.out.println("\n[INFO] Spring ApplicationContext closed.");
        System.out.println("============================================================");
    }
}
