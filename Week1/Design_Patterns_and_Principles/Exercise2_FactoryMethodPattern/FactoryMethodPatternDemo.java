/**
 * FactoryMethodPatternDemo.java
 *
 * <p>Demonstrates the <strong>Factory Method Design Pattern</strong> as part of the
 * Cognizant Nurture Program — Week 1: Design Patterns &amp; Principles.</p>
 *
 * <h2>What is the Factory Method Pattern?</h2>
 * <p>The Factory Method Pattern is a <em>creational design pattern</em> that defines
 * an interface for creating an object, but lets <strong>subclasses decide</strong>
 * which class to instantiate. Factory Method lets a class defer instantiation to
 * subclasses.</p>
 *
 * <h2>Participants (GoF terminology)</h2>
 * <ul>
 *   <li><strong>Product</strong>          — {@code Document} (abstract class)</li>
 *   <li><strong>ConcreteProduct</strong>  — {@code WordDocument}, {@code PdfDocument},
 *       {@code ExcelDocument}</li>
 *   <li><strong>Creator</strong>          — {@code DocumentCreator} (abstract class with
 *       factory method)</li>
 *   <li><strong>ConcreteCreator</strong>  — {@code WordDocumentCreator},
 *       {@code PdfDocumentCreator}, {@code ExcelDocumentCreator}</li>
 * </ul>
 *
 * <h2>Scenario</h2>
 * <p>A <em>Document Management System (DMS)</em> needs to support multiple document
 * formats. New formats can be added without changing existing creator or consumer
 * code — demonstrating the <em>Open/Closed Principle</em>.</p>
 *
 * @author  Cognizant Nurture Program
 * @version 1.0
 */
public class FactoryMethodPatternDemo {

    // =========================================================================
    //  Entry Point
    // =========================================================================

    /**
     * Application entry point.
     *
     * <p>Simulates a Document Management System that creates, opens, saves, and
     * exports several document types through a common abstract interface,
     * showcasing the Factory Method pattern in action.</p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   Factory Method Pattern — Document Management System ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");

        // ------------------------------------------------------------------
        // Build an array of creators — each knows how to make ONE document type.
        // The client code works entirely through the abstract Creator interface.
        // ------------------------------------------------------------------
        DocumentCreator[] creators = {
            new WordDocumentCreator(),
            new PdfDocumentCreator(),
            new ExcelDocumentCreator()
        };

        // ------------------------------------------------------------------
        // Demonstrate polymorphic document creation.
        // The client loop never mentions WordDocument, PdfDocument, or
        // ExcelDocument by name — it only knows about Document and
        // DocumentCreator abstractions.
        // ------------------------------------------------------------------
        for (DocumentCreator creator : creators) {

            System.out.println("┌─────────────────────────────────────────────────────┐");

            // *** THE FACTORY METHOD CALL ***
            // The concrete creator decides which Document subclass to instantiate.
            Document doc = creator.createDocument();

            // All documents share the same interface — classic polymorphism.
            creator.processDocument(doc);

            System.out.println("└─────────────────────────────────────────────────────┘\n");
        }

        // ------------------------------------------------------------------
        // Demonstrate adding a brand-new format (XmlDocument) without
        // touching any of the existing classes — Open/Closed Principle.
        // ------------------------------------------------------------------
        System.out.println("─── Adding a NEW format (XML) without changing existing code ───\n");

        DocumentCreator xmlCreator = new XmlDocumentCreator();
        Document xmlDoc = xmlCreator.createDocument();
        xmlCreator.processDocument(xmlDoc);

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║  Factory Method Pattern Demo Completed Successfully! ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
}


// =============================================================================
//  PRODUCT HIERARCHY
// =============================================================================

/**
 * Abstract base class for all documents in the Document Management System.
 *
 * <p>Defines the <em>Product</em> interface that every concrete document must
 * honour. Factory Method guarantees the client can always treat any document
 * through this contract, regardless of its actual type.</p>
 *
 * <p>Using an abstract class (instead of an interface) allows us to provide
 * default implementations for common behaviours such as {@link #save()} while
 * still forcing subclasses to supply format-specific logic.</p>
 */
abstract class Document {

    // -------------------------------------------------------------------------
    //  Document metadata
    // -------------------------------------------------------------------------

    /** Human-readable name of this document, e.g. "Quarterly Report.docx". */
    private String fileName;

    /** The user-visible content body of the document. */
    private String content;

    /** Tracks whether unsaved changes exist. */
    private boolean modified;

    // -------------------------------------------------------------------------
    //  Constructor
    // -------------------------------------------------------------------------

    /**
     * Initialises a new, empty {@code Document} with the given file name.
     *
     * @param fileName the name to assign to this document (must not be {@code null})
     */
    public Document(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name must not be null or blank.");
        }
        this.fileName = fileName;
        this.content  = "";
        this.modified = false;
    }

    // -------------------------------------------------------------------------
    //  Abstract methods — format-specific behaviour
    // -------------------------------------------------------------------------

    /**
     * Opens the document, performing any format-specific initialisation
     * (e.g., parsing XML structure for an Excel file, decrypting a PDF).
     */
    public abstract void open();

    /**
     * Exports the document to the format-specific default output
     * (e.g., prints to PDF for a PdfDocument).
     */
    public abstract void export();

    /**
     * Returns the MIME type associated with this document format.
     *
     * @return a non-null MIME type string, e.g. {@code "application/pdf"}
     */
    public abstract String getMimeType();

    // -------------------------------------------------------------------------
    //  Concrete shared methods
    // -------------------------------------------------------------------------

    /**
     * Saves the document to disk using the document's {@link #fileName}.
     *
     * <p>This default implementation prints a simulated save operation.
     * Subclasses may override to add format-specific serialisation.</p>
     */
    public void save() {
        System.out.println("  [Save]   Saving '" + fileName + "' (" + getMimeType() + ") ...");
        modified = false;
        System.out.println("  [Save]   '" + fileName + "' saved successfully.");
    }

    /**
     * Closes the document and releases any held resources.
     *
     * <p>Warns the user if there are unsaved changes.</p>
     */
    public void close() {
        if (modified) {
            System.out.println("  [Close]  WARNING — '" + fileName
                + "' has unsaved changes! Closing anyway.");
        } else {
            System.out.println("  [Close]  '" + fileName + "' closed.");
        }
    }

    // -------------------------------------------------------------------------
    //  Getters / Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the file name of this document.
     *
     * @return the file name (never {@code null})
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Replaces the content of this document and marks it as modified.
     *
     * @param content the new document body (must not be {@code null})
     */
    public void setContent(String content) {
        if (content == null) throw new IllegalArgumentException("Content must not be null.");
        this.content  = content;
        this.modified = true;
    }

    /**
     * Returns the current content body of this document.
     *
     * @return the content string (never {@code null})
     */
    public String getContent() {
        return content;
    }
}


// ─────────────────────────────────────────────────────────────────────────────
//  ConcreteProduct 1 — WordDocument
// ─────────────────────────────────────────────────────────────────────────────

/**
 * A Microsoft Word–compatible document ({@code .docx}).
 *
 * <p>Implements format-specific behaviour such as paragraph/style parsing,
 * revision tracking, and mail-merge export.</p>
 */
class WordDocument extends Document {

    /**
     * Creates a new Word document with the specified file name.
     *
     * @param fileName the name for this document (e.g., {@code "Report.docx"})
     */
    public WordDocument(String fileName) {
        super(fileName);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Simulates parsing OOXML markup and loading tracked revisions.</p>
     */
    @Override
    public void open() {
        System.out.println("  [Word]   Opening '" + getFileName()
            + "' — parsing OOXML markup and loading tracked revisions...");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Simulates exporting the document as a PDF via the Word print pipeline.</p>
     */
    @Override
    public void export() {
        System.out.println("  [Word]   Exporting '" + getFileName()
            + "' to PDF via Word print pipeline...");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}
     */
    @Override
    public String getMimeType() {
        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    }
}


// ─────────────────────────────────────────────────────────────────────────────
//  ConcreteProduct 2 — PdfDocument
// ─────────────────────────────────────────────────────────────────────────────

/**
 * An Adobe PDF document ({@code .pdf}).
 *
 * <p>Supports digital signing and form-field extraction on open/export.</p>
 */
class PdfDocument extends Document {

    /**
     * Creates a new PDF document with the specified file name.
     *
     * @param fileName the name for this document (e.g., {@code "Invoice.pdf"})
     */
    public PdfDocument(String fileName) {
        super(fileName);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Simulates verifying digital signatures and extracting form fields.</p>
     */
    @Override
    public void open() {
        System.out.println("  [PDF]    Opening '" + getFileName()
            + "' — verifying digital signatures and extracting form fields...");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Simulates flattening form fields and compressing the output stream.</p>
     */
    @Override
    public void export() {
        System.out.println("  [PDF]    Exporting '" + getFileName()
            + "' — flattening fields and compressing output stream...");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code "application/pdf"}
     */
    @Override
    public String getMimeType() {
        return "application/pdf";
    }
}


// ─────────────────────────────────────────────────────────────────────────────
//  ConcreteProduct 3 — ExcelDocument
// ─────────────────────────────────────────────────────────────────────────────

/**
 * A Microsoft Excel spreadsheet ({@code .xlsx}).
 *
 * <p>Handles formula recalculation and pivot-table refresh on open.</p>
 */
class ExcelDocument extends Document {

    /**
     * Creates a new Excel document with the specified file name.
     *
     * @param fileName the name for this document (e.g., {@code "Budget.xlsx"})
     */
    public ExcelDocument(String fileName) {
        super(fileName);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Simulates recalculating all formula cells and refreshing pivot tables.</p>
     */
    @Override
    public void open() {
        System.out.println("  [Excel]  Opening '" + getFileName()
            + "' — recalculating formula cells and refreshing pivot tables...");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Simulates exporting the active sheet to CSV format.</p>
     */
    @Override
    public void export() {
        System.out.println("  [Excel]  Exporting '" + getFileName()
            + "' — converting active sheet to CSV format...");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}
     */
    @Override
    public String getMimeType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }
}


// ─────────────────────────────────────────────────────────────────────────────
//  ConcreteProduct 4 — XmlDocument  (added later — no existing code changed)
// ─────────────────────────────────────────────────────────────────────────────

/**
 * An XML document ({@code .xml}) added to demonstrate the
 * <em>Open/Closed Principle</em> — the system is open for extension
 * (add this class) but closed for modification (no existing class touched).
 */
class XmlDocument extends Document {

    /**
     * Creates a new XML document with the specified file name.
     *
     * @param fileName the name for this document (e.g., {@code "Config.xml"})
     */
    public XmlDocument(String fileName) {
        super(fileName);
    }

    /** {@inheritDoc} */
    @Override
    public void open() {
        System.out.println("  [XML]    Opening '" + getFileName()
            + "' — parsing DOM tree and validating against XSD schema...");
    }

    /** {@inheritDoc} */
    @Override
    public void export() {
        System.out.println("  [XML]    Exporting '" + getFileName()
            + "' — applying XSLT stylesheet and writing output...");
    }

    /** {@inheritDoc} */
    @Override
    public String getMimeType() {
        return "application/xml";
    }
}


// =============================================================================
//  CREATOR HIERARCHY
// =============================================================================

/**
 * Abstract <em>Creator</em> class that declares the Factory Method.
 *
 * <h2>Key points</h2>
 * <ul>
 *   <li>{@link #createDocument()} is the <strong>factory method</strong> —
 *       abstract here, overridden in each concrete creator.</li>
 *   <li>{@link #processDocument(Document)} is the <em>template method</em>
 *       that defines a standard document-lifecycle workflow using the
 *       factory method to obtain the product.</li>
 * </ul>
 *
 * <p>The creator class may also provide a default implementation of the factory
 * method that returns a sensible default {@code Document} type, but in this
 * design each subclass must supply its own.</p>
 */
abstract class DocumentCreator {

    // -------------------------------------------------------------------------
    //  Factory Method — subclasses MUST override this
    // -------------------------------------------------------------------------

    /**
     * <strong>Factory Method</strong> — creates and returns a {@link Document}
     * of the appropriate concrete type.
     *
     * <p>Subclasses decide <em>which</em> {@code Document} subclass to
     * instantiate; the caller only sees a {@code Document} reference.</p>
     *
     * @return a new, non-null {@link Document} instance
     */
    public abstract Document createDocument();

    // -------------------------------------------------------------------------
    //  Template Method — defines the standard document lifecycle
    // -------------------------------------------------------------------------

    /**
     * Executes the standard document lifecycle:
     * <ol>
     *   <li>Open the document</li>
     *   <li>Write some sample content</li>
     *   <li>Save the document</li>
     *   <li>Export the document</li>
     *   <li>Close the document</li>
     * </ol>
     *
     * <p>This method depends on {@link #createDocument()} to obtain the product,
     * but it never references any concrete document class directly — it always
     * works through the {@link Document} abstraction.</p>
     *
     * @param doc the {@link Document} to process (created by {@link #createDocument()})
     */
    public void processDocument(Document doc) {
        System.out.println("  [DMS]    Processing: " + doc.getFileName()
            + "  (" + doc.getClass().getSimpleName() + ")");

        doc.open();
        doc.setContent("Sample content added by the Document Management System.");
        doc.save();
        doc.export();
        doc.close();
    }
}


// ─────────────────────────────────────────────────────────────────────────────
//  ConcreteCreator 1 — WordDocumentCreator
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Concrete Creator that produces {@link WordDocument} instances.
 *
 * <p>Overrides {@link #createDocument()} to instantiate a {@code WordDocument}
 * with a default file name appropriate for this creator.</p>
 */
class WordDocumentCreator extends DocumentCreator {

    /**
     * {@inheritDoc}
     *
     * @return a new {@link WordDocument} named {@code "Document.docx"}
     */
    @Override
    public Document createDocument() {
        System.out.println("  [Creator] WordDocumentCreator → new WordDocument(\"Document.docx\")");
        return new WordDocument("Document.docx");
    }
}


// ─────────────────────────────────────────────────────────────────────────────
//  ConcreteCreator 2 — PdfDocumentCreator
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Concrete Creator that produces {@link PdfDocument} instances.
 */
class PdfDocumentCreator extends DocumentCreator {

    /**
     * {@inheritDoc}
     *
     * @return a new {@link PdfDocument} named {@code "Report.pdf"}
     */
    @Override
    public Document createDocument() {
        System.out.println("  [Creator] PdfDocumentCreator → new PdfDocument(\"Report.pdf\")");
        return new PdfDocument("Report.pdf");
    }
}


// ─────────────────────────────────────────────────────────────────────────────
//  ConcreteCreator 3 — ExcelDocumentCreator
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Concrete Creator that produces {@link ExcelDocument} instances.
 */
class ExcelDocumentCreator extends DocumentCreator {

    /**
     * {@inheritDoc}
     *
     * @return a new {@link ExcelDocument} named {@code "DataSheet.xlsx"}
     */
    @Override
    public Document createDocument() {
        System.out.println("  [Creator] ExcelDocumentCreator → new ExcelDocument(\"DataSheet.xlsx\")");
        return new ExcelDocument("DataSheet.xlsx");
    }
}


// ─────────────────────────────────────────────────────────────────────────────
//  ConcreteCreator 4 — XmlDocumentCreator  (added later — OCP in action)
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Concrete Creator that produces {@link XmlDocument} instances.
 *
 * <p>Added without touching <em>any</em> of the existing creator or product
 * classes — demonstrating the Open/Closed Principle.</p>
 */
class XmlDocumentCreator extends DocumentCreator {

    /**
     * {@inheritDoc}
     *
     * @return a new {@link XmlDocument} named {@code "Config.xml"}
     */
    @Override
    public Document createDocument() {
        System.out.println("  [Creator] XmlDocumentCreator → new XmlDocument(\"Config.xml\")");
        return new XmlDocument("Config.xml");
    }
}
