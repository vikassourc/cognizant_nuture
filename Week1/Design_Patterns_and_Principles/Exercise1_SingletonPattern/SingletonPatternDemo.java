/**
 * SingletonPatternDemo.java
 *
 * <p>Demonstrates the <strong>Singleton Design Pattern</strong> as part of the
 * Cognizant Nurture Program — Week 1: Design Patterns &amp; Principles.</p>
 *
 * <h2>What is the Singleton Pattern?</h2>
 * <p>The Singleton Pattern is a <em>creational design pattern</em> that ensures a class
 * has only <strong>one instance</strong> throughout the lifetime of an application, and
 * provides a global point of access to that instance.</p>
 *
 * <h2>When to use it?</h2>
 * <ul>
 *   <li>Logging services (exactly one log manager)</li>
 *   <li>Configuration managers (one config reader for the app)</li>
 *   <li>Thread pools or connection pools</li>
 *   <li>Cache managers</li>
 * </ul>
 *
 * <h2>Implementation Strategy Used</h2>
 * <p>This file uses <strong>Double-Checked Locking (DCL)</strong> with a
 * {@code volatile} field — the recommended thread-safe approach that avoids
 * unnecessary synchronization after the instance is already created.</p>
 *
 * @author  Cognizant Nurture Program
 * @version 1.0
 */
public class SingletonPatternDemo {

    // =========================================================================
    //  Entry Point
    // =========================================================================

    /**
     * Application entry point.
     *
     * <p>Demonstrates that:</p>
     * <ol>
     *   <li>Multiple calls to {@code Logger.getInstance()} always return the
     *       <em>same</em> object reference.</li>
     *   <li>The singleton behaves correctly even when accessed from multiple
     *       threads concurrently.</li>
     * </ol>
     *
     * @param args command-line arguments (not used)
     * @throws InterruptedException if the thread-safety test is interrupted
     */
    public static void main(String[] args) throws InterruptedException {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║       Singleton Pattern — Logger Demo            ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // ------------------------------------------------------------------
        // Test 1: Verify single-instance guarantee (same-thread)
        // ------------------------------------------------------------------
        System.out.println("─── Test 1: Single-Instance Guarantee ───────────────");

        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        Logger logger3 = Logger.getInstance();

        System.out.println("logger1 hashCode : " + logger1.hashCode());
        System.out.println("logger2 hashCode : " + logger2.hashCode());
        System.out.println("logger3 hashCode : " + logger3.hashCode());

        System.out.println("\nAre logger1 and logger2 the same instance? " + (logger1 == logger2));
        System.out.println("Are logger2 and logger3 the same instance? " + (logger2 == logger3));

        // ------------------------------------------------------------------
        // Test 2: Use the singleton for actual logging
        // ------------------------------------------------------------------
        System.out.println("\n─── Test 2: Using the Logger Singleton ──────────────");

        logger1.log(Logger.Level.INFO,  "Application started successfully.");
        logger2.log(Logger.Level.DEBUG, "Loading configuration from classpath.");
        logger3.log(Logger.Level.WARN,  "Configuration key 'timeout' not set; using default.");
        logger1.log(Logger.Level.ERROR, "Failed to connect to database — retrying...");

        System.out.println("\nTotal messages logged: " + logger1.getMessageCount());

        // ------------------------------------------------------------------
        // Test 3: Thread-safety verification
        // ------------------------------------------------------------------
        System.out.println("\n─── Test 3: Thread-Safety Verification ──────────────");

        final int THREAD_COUNT = 10;
        Thread[] threads = new Thread[THREAD_COUNT];
        Logger[] instancesFromThreads = new Logger[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instancesFromThreads[index] = Logger.getInstance();
                instancesFromThreads[index].log(
                    Logger.Level.INFO,
                    "Message from Thread-" + index
                );
            }, "Worker-" + i);
        }

        // Start all threads simultaneously
        for (Thread t : threads) t.start();

        // Wait for all threads to complete
        for (Thread t : threads) t.join();

        // Verify all threads got the SAME instance
        boolean allSame = true;
        for (int i = 1; i < THREAD_COUNT; i++) {
            if (instancesFromThreads[i] != instancesFromThreads[0]) {
                allSame = false;
                break;
            }
        }

        System.out.println("\nAll " + THREAD_COUNT
            + " threads received the same Logger instance? " + allSame);
        System.out.println("Total messages logged across all threads: "
            + instancesFromThreads[0].getMessageCount());

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  Singleton Pattern Demo Completed Successfully!  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}


// =============================================================================
//  Singleton Class — Logger
// =============================================================================

/**
 * A thread-safe, application-wide {@code Logger} implemented using the
 * <strong>Singleton Design Pattern</strong> with <em>Double-Checked Locking</em>.
 *
 * <h2>Key Design Decisions</h2>
 * <ul>
 *   <li><strong>Private constructor</strong> — prevents instantiation from outside
 *       the class.</li>
 *   <li><strong>{@code volatile} keyword</strong> — ensures the {@code instance}
 *       reference is always read from/written to main memory, preventing subtle
 *       CPU-cache visibility issues in multi-threaded environments.</li>
 *   <li><strong>Double-Checked Locking (DCL)</strong> — the outer {@code null}
 *       check avoids expensive synchronisation after the instance is created;
 *       the inner check inside {@code synchronized} handles the race condition
 *       where two threads both pass the outer check simultaneously.</li>
 * </ul>
 *
 * <h2>Thread Safety</h2>
 * <p>Safe for use from multiple threads without any external synchronisation.</p>
 */
class Logger {

    // -------------------------------------------------------------------------
    //  Singleton instance — volatile ensures visibility across threads
    // -------------------------------------------------------------------------

    /**
     * The sole instance of {@code Logger}.
     *
     * <p>Declared {@code volatile} to prevent the JVM / CPU from reordering
     * the partially-constructed object assignment before the constructor
     * completes — a subtle bug that can occur without this keyword in
     * Java's memory model.</p>
     */
    private static volatile Logger instance;

    // -------------------------------------------------------------------------
    //  Instance state
    // -------------------------------------------------------------------------

    /** Counts the total number of log messages written through this singleton. */
    private int messageCount = 0;

    // -------------------------------------------------------------------------
    //  Log level enumeration
    // -------------------------------------------------------------------------

    /**
     * Severity levels for log messages, ordered from least to most severe.
     */
    public enum Level {
        /** Fine-grained informational events useful for debugging. */
        DEBUG,
        /** Informational messages highlighting normal application progress. */
        INFO,
        /** Potentially harmful situations that deserve attention. */
        WARN,
        /** Error events that may still allow the application to continue. */
        ERROR
    }

    // -------------------------------------------------------------------------
    //  Private constructor — prevents external instantiation
    // -------------------------------------------------------------------------

    /**
     * Private constructor — the Singleton pattern requires that no external
     * code can call {@code new Logger()}.
     *
     * <p>Initialisation work (e.g., opening a log file) would go here.</p>
     */
    private Logger() {
        // In a real application you might open a file stream, set up
        // a rolling file appender, or configure a remote log sink here.
        System.out.println("[Logger] Instance created (this message appears only once).");
    }

    // -------------------------------------------------------------------------
    //  Public factory method — the global access point
    // -------------------------------------------------------------------------

    /**
     * Returns the single, shared {@code Logger} instance.
     *
     * <h3>Algorithm — Double-Checked Locking</h3>
     * <pre>{@code
     *  if (instance == null) {             // First check  (no lock)
     *      synchronized (Logger.class) {
     *          if (instance == null) {     // Second check (with lock)
     *              instance = new Logger();
     *          }
     *      }
     *  }
     *  return instance;
     * }</pre>
     *
     * <p>The first {@code null} check avoids locking on every call once the
     * instance has been created.  The synchronized block and second {@code null}
     * check together prevent two threads from each creating their own instance
     * when both pass the first check at the same time.</p>
     *
     * @return the singleton {@code Logger} instance (never {@code null})
     */
    public static Logger getInstance() {
        if (instance == null) {                   // 1st check — fast path (no lock)
            synchronized (Logger.class) {
                if (instance == null) {           // 2nd check — inside lock
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    // -------------------------------------------------------------------------
    //  Public API
    // -------------------------------------------------------------------------

    /**
     * Writes a formatted log message to standard output.
     *
     * <p>Format: {@code [LEVEL] HH:MM:SS.mmm — message}</p>
     *
     * @param level   the severity level of the message (must not be {@code null})
     * @param message the text to log (must not be {@code null})
     * @throws IllegalArgumentException if {@code level} or {@code message} is {@code null}
     */
    public synchronized void log(Level level, String message) {
        if (level == null)   throw new IllegalArgumentException("Log level must not be null.");
        if (message == null) throw new IllegalArgumentException("Log message must not be null.");

        messageCount++;

        // Build a simple timestamp from current system time
        long now       = System.currentTimeMillis();
        long hours     = (now / 3_600_000) % 24;
        long minutes   = (now /    60_000) % 60;
        long seconds   = (now /     1_000) % 60;
        long millis    = now % 1_000;

        String timestamp = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);

        // Pad the level name so messages align neatly
        String levelLabel = String.format("%-5s", level.name());

        System.out.printf("[%s] %s — %s%n", levelLabel, timestamp, message);
    }

    /**
     * Returns the total number of log messages written through this logger
     * since the application started.
     *
     * @return a non-negative integer representing the message count
     */
    public synchronized int getMessageCount() {
        return messageCount;
    }
}
