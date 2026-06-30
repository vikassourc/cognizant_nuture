package com.cognizant.comparison;

/**
 * JPA vs Hibernate vs Spring Data JPA – Code Comparison
 *
 * <p>This single Java file demonstrates how to persist the same {@code Product}
 * entity using three different approaches:</p>
 *
 * <ol>
 *   <li><b>Section 1 – Pure JPA</b>: Uses only the {@code jakarta.persistence.*}
 *       specification APIs ({@code EntityManager}, {@code EntityTransaction}).
 *       No Hibernate-specific classes are referenced. Requires {@code persistence.xml}.</li>
 *
 *   <li><b>Section 2 – Hibernate Native</b>: Uses Hibernate's own
 *       {@code SessionFactory} and {@code Session} APIs directly. Gives access to
 *       Hibernate-exclusive features such as HQL extensions, L2 caching, batch
 *       fetching, and {@code StatelessSession}.</li>
 *
 *   <li><b>Section 3 – Spring Data JPA</b>: Uses Spring's {@code JpaRepository}
 *       interface. Zero DAO boilerplate—just define method signatures and Spring
 *       generates the implementation at runtime.</li>
 * </ol>
 *
 * <p><b>Note:</b> This file is intentionally NOT a runnable Spring Boot application.
 * It is a <em>teaching document</em> that illustrates the API differences side-by-side
 * with heavy inline comments. In a real project, you would choose ONE approach
 * and set it up with the appropriate configuration.</p>
 *
 * <p>Sections are separated by large banners so you can quickly jump to the one
 * you are interested in using an IDE's "Go to line" or Ctrl+F.</p>
 *
 * @author  Cognizant Nurture – Java FSE Program
 * @version 1.0
 * @since   Week 2 – Spring Data JPA Module | Exercise 2
 */
public class JPA_vs_Hibernate_vs_SpringDataJPA {

    /*
     * =========================================================================
     * SHARED DOMAIN MODEL
     * =========================================================================
     * All three sections use the same 'Product' entity. The JPA annotations
     * below are STANDARD (from jakarta.persistence.*), meaning they work with
     * ANY JPA provider – not just Hibernate.
     *
     * In a real project, Product.java would be in its own file (entity package).
     * It is placed here as a static nested class for easy side-by-side reference.
     * =========================================================================
     */

    /**
     * Product entity – the same class used across all three demonstration sections.
     *
     * <p>Notice that ALL annotations are from {@code jakarta.persistence.*}
     * (the JPA standard). There are zero Hibernate-specific annotations here.
     * This is a best practice: keep entities portable across JPA providers.</p>
     */
    // --------------- (imagine these imports at the top of the file) ---------------
    // import jakarta.persistence.*;
    // import java.math.BigDecimal;

    /* ── Pseudo-code representation (not compiled directly due to demo structure) ──

    @Entity
    @Table(name = "products")
    public static class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)   // DB auto-increment
        private Long id;

        @Column(name = "name", nullable = false, length = 200)
        private String name;

        @Column(name = "price", nullable = false)
        private double price;

        @Column(name = "category", length = 100)
        private String category;

        @Column(name = "in_stock")
        private boolean inStock = true;

        // JPA REQUIRES a no-arg constructor (used by JPA to instantiate via reflection)
        public Product() {}

        public Product(String name, double price, String category) {
            this.name     = name;
            this.price    = price;
            this.category = category;
        }

        // Standard getters and setters ...
        public Long   getId()       { return id; }
        public String getName()     { return name; }
        public double getPrice()    { return price; }
        public String getCategory() { return category; }
        public boolean isInStock()  { return inStock; }

        public void setId(Long id)            { this.id = id; }
        public void setName(String name)      { this.name = name; }
        public void setPrice(double price)    { this.price = price; }
        public void setCategory(String cat)   { this.category = cat; }
        public void setInStock(boolean stock) { this.inStock = stock; }

        @Override
        public String toString() {
            return "Product{id=" + id + ", name='" + name + "', price=" + price
                   + ", category='" + category + "', inStock=" + inStock + "}";
        }
    }
    ─────────────────────────────────────────────────────────────────────────── */

    /*
     * =========================================================================
     *
     *   ███████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗     ██╗
     *   ██╔════╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║    ███║
     *   ███████╗█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║    ╚██║
     *   ╚════██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║     ██║
     *   ███████║███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║     ██║
     *   ╚══════╝╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝     ╚═╝
     *
     *  SECTION 1: PURE JPA APPROACH
     *  API:    jakarta.persistence.* only
     *  Config: persistence.xml in META-INF/ (no Spring, no Hibernate specifics)
     *  Trade:  Maximum portability, maximum boilerplate
     *
     * =========================================================================
     */

    /**
     * Section 1: Demonstrates the pure JPA approach using only standard
     * {@code jakarta.persistence.*} APIs.
     *
     * <p><b>Required setup (not in this class):</b></p>
     * <ol>
     *   <li>A {@code META-INF/persistence.xml} file that declares the persistence
     *       unit, JDBC connection details, and the JPA provider class.</li>
     *   <li>A JPA provider JAR on the classpath (Hibernate, EclipseLink, etc.).</li>
     * </ol>
     *
     * <p><b>persistence.xml snippet:</b></p>
     * <pre>{@code
     * <persistence xmlns="https://jakarta.ee/xml/ns/persistence" version="3.0">
     *   <persistence-unit name="productPU" transaction-type="RESOURCE_LOCAL">
     *     <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
     *     <class>com.cognizant.comparison.Product</class>
     *     <properties>
     *       <property name="jakarta.persistence.jdbc.url"    value="jdbc:h2:mem:testdb"/>
     *       <property name="jakarta.persistence.jdbc.driver" value="org.h2.Driver"/>
     *       <property name="jakarta.persistence.jdbc.user"   value="sa"/>
     *       <property name="jakarta.persistence.jdbc.password" value=""/>
     *       <property name="hibernate.hbm2ddl.auto"          value="create-drop"/>
     *       <property name="hibernate.show_sql"              value="true"/>
     *     </properties>
     *   </persistence-unit>
     * </persistence>
     * }</pre>
     */
    // import jakarta.persistence.*;
    // import java.util.List;

    static class Section1_PureJPA {

        /* ──────────────────────────────────────────────────────────────────
         * HOW IT WORKS:
         * - Persistence.createEntityManagerFactory() reads persistence.xml
         * - EntityManagerFactory is heavy – create ONCE per application
         * - EntityManager is lightweight – create per request / unit of work
         * - All DB operations need an active transaction (beginTransaction → commit)
         * - You must ALWAYS close EntityManager and EntityManagerFactory
         * ────────────────────────────────────────────────────────────────── */

        // ── STEP 1: Create the EntityManagerFactory (once per application) ──
        //
        // EntityManagerFactory emf =
        //     Persistence.createEntityManagerFactory("productPU");
        //                                              ┗━━ must match persistence.xml
        //
        // ── STEP 2: Create an EntityManager for this unit of work ──
        //
        // EntityManager em = emf.createEntityManager();
        // EntityTransaction tx = em.getTransaction();

        void demonstratePureJPA() {

            /*
             * ── CREATE (INSERT) ─────────────────────────────────────────────
             * em.persist(entity) tells JPA to track this object.
             * The INSERT SQL is not sent immediately – it is batched until
             * the persistence context flushes (either at commit or explicitly).
             *
             * After persist(), 'product' becomes a MANAGED entity.
             * The @GeneratedValue ID is populated after flush/commit.
             */
            // tx.begin();
            // Product product = new Product("Wireless Mouse", 29.99, "Electronics");
            // em.persist(product);   // ← makes 'product' managed; queues INSERT
            // tx.commit();           // ← flushes + commits → INSERT runs here
            // System.out.println("Saved product with ID: " + product.getId());

            /*
             * ── READ (SELECT by PK) ─────────────────────────────────────────
             * em.find() issues SELECT immediately (no lazy loading here).
             * Returns null if not found (unlike getReference() which throws on access).
             *
             * The entity returned is MANAGED – any changes within a transaction
             * will be automatically persisted (dirty checking).
             */
            // Product found = em.find(Product.class, 1L);
            // System.out.println("Found: " + found);

            /*
             * ── READ (JPQL query) ────────────────────────────────────────────
             * JPQL uses ENTITY class names and FIELD names, not table/column names.
             * 'Product' = Java class name, 'p.category' = Java field name.
             * JPA translates this to SQL: SELECT * FROM products WHERE category = ?
             *
             * TypedQuery<T> avoids the unchecked cast that Query requires.
             */
            // TypedQuery<Product> query = em.createQuery(
            //     "SELECT p FROM Product p WHERE p.category = :cat",
            //     Product.class
            // );
            // query.setParameter("cat", "Electronics");
            // List<Product> electronics = query.getResultList();
            // electronics.forEach(System.out::println);

            /*
             * ── UPDATE ──────────────────────────────────────────────────────
             * For a MANAGED entity (fetched in the current transaction),
             * simply change the field value. Hibernate's dirty-checking mechanism
             * detects the change at flush time and issues an UPDATE automatically.
             * No explicit "update" method call is needed.
             *
             * For a DETACHED entity (fetched in a previous transaction),
             * use em.merge(detachedEntity) to re-attach and synchronize.
             */
            // tx.begin();
            // Product toUpdate = em.find(Product.class, 1L);  // now MANAGED
            // toUpdate.setPrice(24.99);                        // just change the field
            // // No em.save() needed! Dirty checking handles the UPDATE SQL.
            // tx.commit();

            /*
             * ── DELETE ──────────────────────────────────────────────────────
             * em.remove() schedules the entity for deletion.
             * The DELETE SQL is issued on flush/commit.
             * The entity must be in MANAGED state before removal.
             */
            // tx.begin();
            // Product toDelete = em.find(Product.class, 1L);  // get managed reference
            // em.remove(toDelete);                             // schedule DELETE
            // tx.commit();                                     // DELETE runs here

            /*
             * ── COUNT (Aggregate JPQL) ───────────────────────────────────────
             * JPQL aggregate functions work like SQL: COUNT, SUM, AVG, MIN, MAX.
             * Returns Long for COUNT queries.
             */
            // Long count = em.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
            //               .getSingleResult();
            // System.out.println("Total products: " + count);

            /*
             * ── CLEANUP ─────────────────────────────────────────────────────
             * Always close EntityManager to release the persistence context
             * and database connection back to the pool.
             */
            // em.close();
            // emf.close();  // only at application shutdown

            System.out.println("[Section 1 - Pure JPA] See inline comments above for API usage.");
            System.out.println("Key classes: Persistence, EntityManagerFactory, EntityManager, EntityTransaction, TypedQuery");
        }
    }


    /*
     * =========================================================================
     *
     *  ███████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗    ██████╗
     *  ██╔════╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║    ╚════██╗
     *  ███████╗█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║     █████╔╝
     *  ╚════██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║    ██╔═══╝
     *  ███████║███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║    ███████╗
     *  ╚══════╝╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝    ╚══════╝
     *
     *  SECTION 2: HIBERNATE NATIVE APPROACH
     *  API:    org.hibernate.* (SessionFactory, Session, HQL)
     *  Config: hibernate.cfg.xml or programmatic Configuration object
     *  Trade:  Maximum power + Hibernate-exclusive features, but vendor lock-in
     *
     * =========================================================================
     */

    /**
     * Section 2: Demonstrates the Hibernate native API approach.
     *
     * <p>Uses {@code org.hibernate.SessionFactory} and {@code org.hibernate.Session}
     * instead of the JPA {@code EntityManagerFactory} / {@code EntityManager}.
     * While these are Hibernate-specific classes, Hibernate's {@code Session}
     * actually <em>extends</em> JPA's {@code EntityManager} since Hibernate 5.2+,
     * so all JPA methods are available on it too.</p>
     *
     * <p><b>hibernate.cfg.xml snippet (alternative to persistence.xml):</b></p>
     * <pre>{@code
     * <hibernate-configuration>
     *   <session-factory>
     *     <property name="hibernate.connection.url">jdbc:h2:mem:testdb</property>
     *     <property name="hibernate.connection.driver_class">org.h2.Driver</property>
     *     <property name="hibernate.connection.username">sa</property>
     *     <property name="hibernate.connection.password"></property>
     *     <property name="hibernate.hbm2ddl.auto">create-drop</property>
     *     <property name="hibernate.show_sql">true</property>
     *     <mapping class="com.cognizant.comparison.Product"/>
     *   </session-factory>
     * </hibernate-configuration>
     * }</pre>
     */
    // import org.hibernate.*;
    // import org.hibernate.cfg.Configuration;
    // import java.util.List;

    static class Section2_HibernateNative {

        /* ──────────────────────────────────────────────────────────────────
         * HOW IT DIFFERS FROM JPA:
         * - SessionFactory (like EntityManagerFactory) = heavy, create once
         * - Session (like EntityManager) = lightweight, one per unit of work
         * - Session.save() = Hibernate native; Session.persist() = JPA-aligned
         * - Use HQL (Hibernate Query Language) instead of JPQL; syntax is similar
         *   but HQL has extra functions and features
         * - First-Level Cache is per-Session and always ON (no config needed)
         * ────────────────────────────────────────────────────────────────── */

        // ── STEP 1: Build SessionFactory (once per application) ──
        //
        // Configuration config = new Configuration();
        // config.configure("hibernate.cfg.xml");      // or programmatic config
        // SessionFactory sessionFactory = config.buildSessionFactory();

        void demonstrateHibernateNative() {

            /*
             * ── OPEN SESSION ─────────────────────────────────────────────────
             * openSession() creates a new Session connected to the DB.
             * The try-with-resources block ensures it is ALWAYS closed, even on exception.
             * Closing the Session releases the DB connection back to the pool.
             */
            // try (Session session = sessionFactory.openSession()) {

            /*
             * ── CREATE (INSERT) via Hibernate native save() ─────────────────
             * session.save() is the Hibernate-specific way to persist.
             * Returns the generated PK (unlike em.persist() which is void).
             * Prefer session.persist() for JPA compatibility; save() is legacy.
             *
             * Note: session.saveOrUpdate() is Hibernate's equivalent of JPA merge()
             * – it inserts if new (id=null) or updates if exists.
             */
            //     Transaction tx = session.beginTransaction();
            //
            //     Product product = new Product("Mechanical Keyboard", 149.99, "Electronics");
            //     Long generatedId = (Long) session.save(product);
            //     System.out.println("Saved with generated ID: " + generatedId);
            //
            //     tx.commit();

            /*
             * ── READ via session.get() ───────────────────────────────────────
             * session.get() is like em.find() – returns null if not found.
             * session.load() returns a PROXY (throws ObjectNotFoundException on access
             * if the record doesn't exist) – useful when you just need the reference.
             *
             * First-Level Cache: if the same ID is requested again in the SAME session,
             * Hibernate returns the cached instance without hitting the DB again.
             */
            //     Product found = session.get(Product.class, generatedId);
            //     System.out.println("Found: " + found);
            //
            //     // Hibernate L1 Cache Demo: same session, same ID → NO second DB hit
            //     Product foundAgain = session.get(Product.class, generatedId);
            //     System.out.println("Same instance? " + (found == foundAgain)); // true!

            /*
             * ── HQL (Hibernate Query Language) ──────────────────────────────
             * HQL is a superset of JPQL. Syntax is nearly identical, but HQL
             * supports extra features:
             *   - LIMIT / OFFSET (in newer Hibernate versions)
             *   - str(), bit_length(), extract() and other Hibernate-defined functions
             *   - Direct collection JOIN without needing a JOIN FETCH
             *
             * Like JPQL, HQL operates on ENTITY class names and FIELD names.
             */
            //     List<Product> electronics = session
            //         .createQuery("FROM Product p WHERE p.category = :cat", Product.class)
            //         .setParameter("cat", "Electronics")
            //         .list();
            //     System.out.println("Electronics count: " + electronics.size());

            /*
             * ── NATIVE SQL via Hibernate ─────────────────────────────────────
             * When HQL/JPQL can't express what you need (DB-specific functions,
             * complex joins, CTEs), use createNativeQuery() to run raw SQL.
             * Hibernate wraps the ResultSet in entity objects via @SqlResultSetMapping
             * or as Object[] if no mapping is provided.
             */
            //     @SuppressWarnings("unchecked")
            //     List<Product> raw = session
            //         .createNativeQuery("SELECT * FROM products WHERE in_stock = TRUE", Product.class)
            //         .list();
            //     System.out.println("In-stock products (native SQL): " + raw.size());

            /*
             * ── UPDATE via dirty checking ────────────────────────────────────
             * Exactly like JPA: modify the managed entity's fields within a
             * transaction; Hibernate detects the change (dirty checking) and
             * issues the UPDATE SQL at flush time.
             *
             * session.update(detachedEntity) re-attaches a detached entity.
             * session.merge(detachedEntity)  is the JPA-standard way (also works).
             */
            //     tx = session.beginTransaction();
            //     Product toUpdate = session.get(Product.class, generatedId);
            //     toUpdate.setPrice(139.99);  // Just set the field
            //     // Hibernate's dirty-check at flush detects the change → UPDATE SQL
            //     tx.commit();

            /*
             * ── DELETE ──────────────────────────────────────────────────────
             * session.delete() is the Hibernate-native method.
             * session.remove() is the JPA-standard equivalent (works too in Hibernate 5.2+).
             */
            //     tx = session.beginTransaction();
            //     Product toDelete = session.get(Product.class, generatedId);
            //     session.delete(toDelete);
            //     tx.commit();
            //     System.out.println("Deleted product ID: " + generatedId);

            /*
             * ── STATELESS SESSION (Ultra-High Performance Batch) ─────────────
             * StatelessSession is Hibernate-EXCLUSIVE (no JPA equivalent).
             * It bypasses the L1 cache and dirty-checking entirely.
             * Ideal for bulk INSERT/UPDATE/DELETE of millions of rows.
             * WARNING: No caching, no lazy loading, no automatic dirty-checking.
             */
            // try (StatelessSession stateless = sessionFactory.openStatelessSession()) {
            //     Transaction batchTx = stateless.beginTransaction();
            //     for (int i = 0; i < 100_000; i++) {
            //         stateless.insert(new Product("Bulk Item " + i, 1.99, "Clearance"));
            //     }
            //     batchTx.commit();
            // }

            // } // end try-with-resources (session.close() is called automatically)

            System.out.println("[Section 2 - Hibernate Native] See inline comments above for API usage.");
            System.out.println("Key classes: Configuration, SessionFactory, Session, Transaction, StatelessSession");
            System.out.println("Key features: HQL, L1/L2 Cache, StatelessSession (batch), Native SQL");
        }
    }


    /*
     * =========================================================================
     *
     *  ███████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗     ██████╗
     *  ██╔════╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║    ╚════██╗
     *  ███████╗█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║        ██╔╝
     *  ╚════██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║       ██╔╝
     *  ███████║███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║      ██╔╝
     *  ╚══════╝╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝     ╚═╝
     *
     *  SECTION 3: SPRING DATA JPA APPROACH
     *  API:    org.springframework.data.jpa.repository.JpaRepository
     *  Config: application.properties (Spring Boot auto-configures everything)
     *  Trade:  Minimal code, maximum productivity; Hibernate still runs under hood
     *
     * =========================================================================
     */

    /**
     * Section 3: Demonstrates the Spring Data JPA approach.
     *
     * <p>In this approach, there is <b>no DAO class to write</b>.
     * You define an interface extending {@code JpaRepository<Product, Long>}
     * and Spring generates a full implementation at startup via dynamic proxies.
     * All the {@code EntityManager} interactions from Section 1 are handled
     * internally by {@code SimpleJpaRepository} (Spring Data JPA's default implementation).</p>
     *
     * <p><b>Required application.properties (Spring Boot):</b></p>
     * <pre>{@code
     * spring.datasource.url=jdbc:h2:mem:productdb
     * spring.datasource.driver-class-name=org.h2.Driver
     * spring.jpa.hibernate.ddl-auto=create-drop
     * spring.jpa.show-sql=true
     * }</pre>
     *
     * <p><b>No persistence.xml needed!</b> Spring Boot auto-configures Hibernate
     * as the JPA provider, creates the {@code EntityManagerFactory},
     * configures connection pooling (HikariCP), and transaction management
     * (via {@code @EnableTransactionManagement})—all from {@code application.properties}.</p>
     */

    /*
     * ── REPOSITORY INTERFACE DEFINITION ──────────────────────────────────────
     *
     * This is ALL you need to declare. No @Repository class, no @Autowired
     * EntityManager, no beginTransaction/commit boilerplate.
     *
     * Spring Data JPA generates the implementation at runtime.
     * ─────────────────────────────────────────────────────────────────────────
     *
     * import org.springframework.data.jpa.repository.JpaRepository;
     * import org.springframework.data.jpa.repository.Query;
     * import org.springframework.data.repository.query.Param;
     * import org.springframework.data.domain.Page;
     * import org.springframework.data.domain.Pageable;
     * import java.util.List;
     *
     * @Repository  // optional – Spring detects it automatically from JpaRepository
     * public interface ProductRepository extends JpaRepository<Product, Long> {
     *
     *     // ── DERIVED QUERY METHODS ──────────────────────────────────────────
     *     // Spring Data JPA parses the method name and generates JPQL automatically.
     *     // No @Query annotation needed for these common patterns.
     *
     *     // Generates: SELECT p FROM Product p WHERE p.category = ?
     *     List<Product> findByCategory(String category);
     *
     *     // Generates: SELECT p FROM Product p WHERE p.price < ?
     *     List<Product> findByPriceLessThan(double maxPrice);
     *
     *     // Generates: WHERE LOWER(p.name) LIKE LOWER('%?%')
     *     List<Product> findByNameContainingIgnoreCase(String keyword);
     *
     *     // Multiple conditions: WHERE category = ? AND price < ?
     *     List<Product> findByCategoryAndPriceLessThan(String category, double maxPrice);
     *
     *     // Existence check: SELECT COUNT(*) > 0 WHERE name = ?
     *     boolean existsByName(String name);
     *
     *     // Count by category: SELECT COUNT(*) WHERE category = ?
     *     long countByCategory(String category);
     *
     *     // ── EXPLICIT @QUERY ────────────────────────────────────────────────
     *     // When derivation is too verbose or impossible, write your own JPQL.
     *
     *     @Query("SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max")
     *     List<Product> findProductsInPriceRange(@Param("min") double min,
     *                                            @Param("max") double max);
     *
     *     // Native SQL query (bypass JPQL entirely)
     *     @Query(value = "SELECT * FROM products WHERE in_stock = 1", nativeQuery = true)
     *     List<Product> findAllInStock();
     *
     *     // ── PAGINATION ─────────────────────────────────────────────────────
     *     // Return Page<T> or Slice<T> and add Pageable parameter for pagination.
     *     Page<Product> findByCategory(String category, Pageable pageable);
     *
     *     // ── MODIFYING QUERIES ──────────────────────────────────────────────
     *     // For UPDATE and DELETE, add @Modifying + @Transactional.
     *     @Modifying
     *     @Transactional
     *     @Query("UPDATE Product p SET p.price = :newPrice WHERE p.category = :cat")
     *     int bulkUpdatePriceByCategory(@Param("newPrice") double price,
     *                                   @Param("cat") String category);
     * }
     */

    /*
     * ── SERVICE CLASS USING THE REPOSITORY ───────────────────────────────────
     *
     * import org.springframework.beans.factory.annotation.Autowired;
     * import org.springframework.data.domain.Page;
     * import org.springframework.data.domain.PageRequest;
     * import org.springframework.data.domain.Sort;
     * import org.springframework.stereotype.Service;
     * import org.springframework.transaction.annotation.Transactional;
     *
     * @Service
     * @Transactional   ← All methods are wrapped in DB transactions automatically
     * public class ProductService {
     *
     *     private final ProductRepository productRepository;
     *
     *     @Autowired   ← Spring injects the auto-generated ProductRepository implementation
     *     public ProductService(ProductRepository repo) {
     *         this.productRepository = repo;
     *     }
     *
     *     // ── CREATE ────────────────────────────────────────────────────────
     *     // save() handles both INSERT (id=null) and UPDATE (id=existing)
     *     public Product createProduct(Product product) {
     *         return productRepository.save(product);
     *         // Equivalent JPA: em.persist(product) → Hibernate executes INSERT SQL
     *     }
     *
     *     // ── READ ALL ──────────────────────────────────────────────────────
     *     @Transactional(readOnly = true)   // tells Hibernate to skip dirty-checking
     *     public List<Product> getAllProducts() {
     *         return productRepository.findAll();
     *         // Equivalent JPA: em.createQuery("SELECT p FROM Product p").getResultList()
     *     }
     *
     *     // ── READ BY ID ────────────────────────────────────────────────────
     *     @Transactional(readOnly = true)
     *     public Optional<Product> getProductById(Long id) {
     *         return productRepository.findById(id);
     *         // Equivalent JPA: Optional.ofNullable(em.find(Product.class, id))
     *     }
     *
     *     // ── UPDATE ────────────────────────────────────────────────────────
     *     public Product updateProduct(Long id, Product details) {
     *         Product existing = productRepository.findById(id)
     *             .orElseThrow(() -> new RuntimeException("Product not found: " + id));
     *         existing.setName(details.getName());
     *         existing.setPrice(details.getPrice());
     *         existing.setCategory(details.getCategory());
     *         return productRepository.save(existing);  // Hibernate issues UPDATE SQL
     *     }
     *
     *     // ── DELETE ────────────────────────────────────────────────────────
     *     public void deleteProduct(Long id) {
     *         productRepository.deleteById(id);
     *         // Equivalent JPA: em.remove(em.find(Product.class, id))
     *     }
     *
     *     // ── PAGINATION ────────────────────────────────────────────────────
     *     @Transactional(readOnly = true)
     *     public Page<Product> getProductsPaged(int page, int size) {
     *         // PageRequest.of(pageNumber, pageSize, sort)
     *         // Hibernate generates: SELECT ... LIMIT ? OFFSET ?
     *         return productRepository.findAll(
     *             PageRequest.of(page, size, Sort.by("price").descending())
     *         );
     *     }
     *
     *     // ── CUSTOM QUERY ──────────────────────────────────────────────────
     *     @Transactional(readOnly = true)
     *     public List<Product> getProductsInRange(double min, double max) {
     *         return productRepository.findProductsInPriceRange(min, max);
     *     }
     * }
     */

    static class Section3_SpringDataJPA {

        void demonstrateSpringDataJPA() {
            System.out.println("[Section 3 - Spring Data JPA] See inline comments above for API usage.");
            System.out.println("Key interface: JpaRepository<T, ID>");
            System.out.println("Key features: Derived queries, @Query, @Modifying, Pageable, Sort, @Transactional");
            System.out.println("Config: application.properties + @SpringBootApplication (no persistence.xml!)");
            System.out.println();
            System.out.println("Generated CRUD methods (free from JpaRepository):");
            System.out.println("  save(entity)           → INSERT or UPDATE");
            System.out.println("  findById(id)           → SELECT by PK");
            System.out.println("  findAll()              → SELECT *");
            System.out.println("  deleteById(id)         → DELETE by PK");
            System.out.println("  count()                → SELECT COUNT(*)");
            System.out.println("  existsById(id)         → SELECT COUNT(*) > 0");
            System.out.println("  saveAll(list)          → batch INSERT/UPDATE");
            System.out.println("  findAll(Pageable)      → SELECT ... LIMIT ? OFFSET ?");
        }
    }


    /*
     * =========================================================================
     * MAIN – Run all three demonstration sections
     * =========================================================================
     */

    /**
     * Main method – executes all three demonstration sections sequentially.
     *
     * <p>Since this is a teaching file with most real code commented out
     * (it would need full Spring context / Hibernate config to run),
     * this main method prints explanatory messages for each section.</p>
     *
     * <p>To see real working code in action, run the Spring Boot application
     * in Exercise 1: {@code SpringDataJpaQuickExampleApplication.java}</p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("  JPA vs Hibernate vs Spring Data JPA – Comparison Demo");
        System.out.println("  Cognizant Nurture | Week 2 | Spring Data JPA Module");
        System.out.println("=".repeat(70));

        System.out.println("\n" + "─".repeat(70));
        System.out.println("  SECTION 1: PURE JPA (jakarta.persistence.*)");
        System.out.println("─".repeat(70));
        new Section1_PureJPA().demonstratePureJPA();

        System.out.println("\n" + "─".repeat(70));
        System.out.println("  SECTION 2: HIBERNATE NATIVE API (org.hibernate.*)");
        System.out.println("─".repeat(70));
        new Section2_HibernateNative().demonstrateHibernateNative();

        System.out.println("\n" + "─".repeat(70));
        System.out.println("  SECTION 3: SPRING DATA JPA (org.springframework.data.jpa.*)");
        System.out.println("─".repeat(70));
        new Section3_SpringDataJPA().demonstrateSpringDataJPA();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("  SUMMARY: API Surface Comparison");
        System.out.println("=".repeat(70));
        System.out.println();
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Operation", "Pure JPA", "Hibernate Native", "Spring Data JPA");
        System.out.println("  " + "─".repeat(90));
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Insert entity",          "em.persist()",   "session.save()",   "repo.save()");
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Select by PK",           "em.find()",      "session.get()",    "repo.findById()");
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Select all",             "createQuery()",  "createQuery()",    "repo.findAll()");
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Update (managed)",       "dirty-check",    "dirty-check",      "repo.save()");
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Delete by PK",           "em.remove()",    "session.delete()", "repo.deleteById()");
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Custom query",           "createQuery()",  "createQuery(HQL)", "findByX() / @Query");
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Count",                  "JPQL COUNT",     "HQL COUNT",        "repo.count()");
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Pagination",             "setFirstResult", "setFirstResult",   "Pageable param");
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Transaction",            "manual",         "manual",           "@Transactional");
        System.out.printf("  %-30s %-15s %-20s %-25s%n",
                "Config",                 "persistence.xml","hibernate.cfg.xml","application.properties");
        System.out.println();
        System.out.println("  For a running demo of Spring Data JPA, see:");
        System.out.println("  → Exercise1_SpringDataJPA_QuickExample/");
        System.out.println("    (SpringDataJpaQuickExampleApplication.java)");
        System.out.println("=".repeat(70));
    }
}
