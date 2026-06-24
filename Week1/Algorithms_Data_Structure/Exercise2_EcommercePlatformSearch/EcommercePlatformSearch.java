import java.util.Arrays;
import java.util.Comparator;

/**
 * EcommercePlatformSearch.java
 *
 * <p>Demonstrates two fundamental search algorithms applied to an e-commerce product catalog:
 * <ul>
 *   <li><b>Linear Search</b> – searches by product name. Time Complexity: O(n) in the worst case
 *       because every element may need to be examined before finding a match (or concluding
 *       the item is absent). Space Complexity: O(1).</li>
 *   <li><b>Binary Search</b> – searches by product ID on a <em>sorted</em> array. Time Complexity:
 *       O(log n) because the search space is halved on every comparison. Space Complexity: O(1)
 *       for the iterative version implemented here.</li>
 * </ul>
 *
 * <p><b>Why does the choice matter?</b><br>
 * For a catalog of 1,000,000 products:
 * <ul>
 *   <li>Linear Search may perform up to 1,000,000 comparisons.</li>
 *   <li>Binary Search performs at most log₂(1,000,000) ≈ 20 comparisons.</li>
 * </ul>
 * Binary Search is therefore dramatically more efficient for large data sets, but requires
 * the array to be pre-sorted on the search key.
 *
 * @author  Cognizant Nurture – Week 1, Exercise 2
 * @version 1.0
 */
public class EcommercePlatformSearch {

    // -------------------------------------------------------------------------
    // Inner class: Product
    // -------------------------------------------------------------------------

    /**
     * Represents a product in the e-commerce catalog.
     */
    static class Product {
        /** Unique identifier for the product. Used as the key for Binary Search. */
        private final int id;

        /** Human-readable product name. Used as the key for Linear Search. */
        private final String name;

        /** Category the product belongs to (e.g., "Electronics"). */
        private final String category;

        /** Retail price in USD. */
        private final double price;

        /**
         * Constructs a new Product with the specified attributes.
         *
         * @param id       unique product identifier
         * @param name     product display name
         * @param category product category
         * @param price    retail price in USD
         */
        public Product(int id, String name, String category, double price) {
            this.id       = id;
            this.name     = name;
            this.category = category;
            this.price    = price;
        }

        // Getters
        public int    getId()       { return id; }
        public String getName()     { return name; }
        public String getCategory() { return category; }
        public double getPrice()    { return price; }

        /**
         * Returns a formatted string representation of the product.
         *
         * @return formatted product details
         */
        @Override
        public String toString() {
            return String.format(
                "Product{id=%-4d name=%-30s category=%-15s price=$%.2f}",
                id, name, category, price
            );
        }
    }

    // -------------------------------------------------------------------------
    // Search Algorithms
    // -------------------------------------------------------------------------

    /**
     * Performs a <b>Linear Search</b> on the given product array by product name.
     *
     * <p><b>Algorithm:</b> Iterates through every element from index 0 to n-1, comparing
     * each product's name to the target. The search is case-insensitive.</p>
     *
     * <p><b>Time Complexity:</b>
     * <ul>
     *   <li>Best Case  – O(1) : target is the first element.</li>
     *   <li>Average    – O(n/2) ≡ O(n) : target is somewhere in the middle.</li>
     *   <li>Worst Case – O(n) : target is the last element or not present at all.</li>
     * </ul>
     * </p>
     *
     * <p><b>Space Complexity:</b> O(1) – only a loop counter is used; no extra data structure.</p>
     *
     * <p><b>Use-case fit:</b> Best suited for small catalogs or unsorted data. For sorted data
     * or large catalogs, Binary Search is preferred.</p>
     *
     * @param products  array of Product objects to search (need not be sorted)
     * @param targetName the product name to search for (case-insensitive)
     * @return the matching {@link Product}, or {@code null} if not found
     */
    public static Product linearSearchByName(Product[] products, String targetName) {
        // Edge case: null or empty array
        if (products == null || products.length == 0) {
            System.out.println("[LinearSearch] The product catalog is empty.");
            return null;
        }
        // Edge case: null / blank search term
        if (targetName == null || targetName.isBlank()) {
            System.out.println("[LinearSearch] Search term must not be empty.");
            return null;
        }

        // Iterate through each product — O(n)
        for (int i = 0; i < products.length; i++) {
            if (products[i].getName().equalsIgnoreCase(targetName)) {
                System.out.printf("[LinearSearch] '%s' found at index %d after %d comparison(s).%n",
                                  targetName, i, i + 1);
                return products[i];
            }
        }

        // Target not found
        System.out.printf("[LinearSearch] '%s' not found after %d comparison(s).%n",
                          targetName, products.length);
        return null;
    }

    /**
     * Performs an iterative <b>Binary Search</b> on the given product array by product ID.
     *
     * <p><b>Pre-condition:</b> The {@code products} array MUST be sorted in ascending order
     * of product ID before calling this method. Binary Search produces incorrect results on
     * an unsorted array.</p>
     *
     * <p><b>Algorithm:</b>
     * <ol>
     *   <li>Set {@code low = 0} and {@code high = n - 1}.</li>
     *   <li>Calculate {@code mid = low + (high - low) / 2}.</li>
     *   <li>If {@code products[mid].id == targetId}, return the product.</li>
     *   <li>If {@code products[mid].id < targetId}, move {@code low = mid + 1}.</li>
     *   <li>Else move {@code high = mid - 1}.</li>
     *   <li>Repeat until found or search space is exhausted.</li>
     * </ol>
     * </p>
     *
     * <p><b>Time Complexity:</b>
     * <ul>
     *   <li>Best Case  – O(1)     : target is the middle element on the first probe.</li>
     *   <li>Average    – O(log n) : target is located after a few halvings.</li>
     *   <li>Worst Case – O(log n) : target is absent; search halves until low > high.</li>
     * </ul>
     * </p>
     *
     * <p><b>Space Complexity:</b> O(1) – iterative approach; no call-stack growth.</p>
     *
     * @param sortedProducts array of {@link Product} objects sorted by {@code id} (ascending)
     * @param targetId       the product ID to search for
     * @return the matching {@link Product}, or {@code null} if not found
     */
    public static Product binarySearchById(Product[] sortedProducts, int targetId) {
        // Edge case: null or empty array
        if (sortedProducts == null || sortedProducts.length == 0) {
            System.out.println("[BinarySearch] The product catalog is empty.");
            return null;
        }

        int low         = 0;
        int high        = sortedProducts.length - 1;
        int comparisons = 0;

        while (low <= high) {
            // Use low + (high - low) / 2 to prevent integer overflow
            int mid = low + (high - low) / 2;
            comparisons++;

            int midId = sortedProducts[mid].getId();

            if (midId == targetId) {
                System.out.printf("[BinarySearch] ID %d found at index %d after %d comparison(s).%n",
                                  targetId, mid, comparisons);
                return sortedProducts[mid];
            } else if (midId < targetId) {
                low = mid + 1;   // Target is in the right half
            } else {
                high = mid - 1;  // Target is in the left half
            }
        }

        // Target not found
        System.out.printf("[BinarySearch] ID %d not found after %d comparison(s).%n",
                          targetId, comparisons);
        return null;
    }

    // -------------------------------------------------------------------------
    // Helper utilities
    // -------------------------------------------------------------------------

    /**
     * Prints a formatted divider line to the console.
     *
     * @param title section title to display inside the divider
     */
    private static void printSection(String title) {
        System.out.println("\n" + "=".repeat(65));
        System.out.println("  " + title);
        System.out.println("=".repeat(65));
    }

    /**
     * Prints the entire product catalog in a table format.
     *
     * @param products the catalog to display
     * @param label    a label shown above the table
     */
    private static void printCatalog(Product[] products, String label) {
        System.out.println("\n--- " + label + " ---");
        System.out.printf("%-4s  %-30s  %-15s  %s%n", "ID", "Name", "Category", "Price (USD)");
        System.out.println("-".repeat(63));
        for (Product p : products) {
            System.out.printf("%-4d  %-30s  %-15s  $%.2f%n",
                              p.getId(), p.getName(), p.getCategory(), p.getPrice());
        }
    }

    // -------------------------------------------------------------------------
    // Main method
    // -------------------------------------------------------------------------

    /**
     * Entry point – builds a sample product catalog and demonstrates both search algorithms.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // ------------------------------------------------------------------
        // 1. Build a realistic product catalog (12 products)
        // ------------------------------------------------------------------
        Product[] catalog = {
            new Product(101, "Samsung Galaxy S24 Ultra",    "Electronics",  1299.99),
            new Product(204, "Apple MacBook Pro 14\"",      "Electronics",  1999.00),
            new Product( 37, "Nike Air Max 2024",           "Footwear",      189.95),
            new Product(315, "Levi's 501 Original Jeans",  "Clothing",       69.99),
            new Product( 58, "Sony WH-1000XM5 Headphones", "Electronics",   349.00),
            new Product(422, "Instant Pot Duo 7-in-1",     "Kitchen",        99.95),
            new Product( 19, "The Pragmatic Programmer",   "Books",           45.00),
            new Product(530, "Adidas Ultraboost 23",       "Footwear",       180.00),
            new Product( 77, "Kindle Paperwhite 11th Gen", "Electronics",   139.99),
            new Product(268, "LEGO Technic Bugatti",       "Toys",           449.99),
            new Product(  5, "Yoga Mat – Extra Thick",     "Sports",          35.50),
            new Product(388, "Dyson V15 Detect Vacuum",    "Home Appliance", 749.99),
        };

        printSection("E-COMMERCE PLATFORM SEARCH DEMO");
        printCatalog(catalog, "Full Product Catalog (Unsorted)");

        // ------------------------------------------------------------------
        // 2. LINEAR SEARCH – search by product name (no sorting required)
        // ------------------------------------------------------------------
        printSection("LINEAR SEARCH  –  O(n)  |  Search by Name");
        System.out.println("  No sorting needed; scans from the first element to the last.\n");

        String[] namesToSearch = {
            "Sony WH-1000XM5 Headphones",   // exists – middle of array
            "Yoga Mat – Extra Thick",        // exists – near end
            "Google Pixel 8 Pro"             // does NOT exist
        };

        for (String name : namesToSearch) {
            System.out.printf("%nSearching for: \"%s\"%n", name);
            Product result = linearSearchByName(catalog, name);
            if (result != null) {
                System.out.println("Result  : " + result);
            } else {
                System.out.println("Result  : Not found in catalog.");
            }
        }

        // ------------------------------------------------------------------
        // 3. BINARY SEARCH – search by product ID (array MUST be sorted first)
        // ------------------------------------------------------------------
        printSection("BINARY SEARCH  –  O(log n)  |  Search by Product ID");
        System.out.println("  Array must be sorted by ID before binary search is applied.\n");

        // Sort a copy of the catalog by product ID (ascending)
        Product[] sortedCatalog = Arrays.copyOf(catalog, catalog.length);
        Arrays.sort(sortedCatalog, Comparator.comparingInt(Product::getId));

        printCatalog(sortedCatalog, "Sorted Catalog (by ID)");
        System.out.println();

        int[] idsToSearch = { 5, 268, 530, 999 };  // 999 does NOT exist

        for (int id : idsToSearch) {
            System.out.printf("%nSearching for ID: %d%n", id);
            Product result = binarySearchById(sortedCatalog, id);
            if (result != null) {
                System.out.println("Result  : " + result);
            } else {
                System.out.println("Result  : No product with ID " + id + " found.");
            }
        }

        // ------------------------------------------------------------------
        // 4. Edge-case demonstrations
        // ------------------------------------------------------------------
        printSection("EDGE-CASE HANDLING");

        System.out.println("\n[Test] Linear Search on empty array:");
        linearSearchByName(new Product[0], "Samsung");

        System.out.println("\n[Test] Linear Search with null search term:");
        linearSearchByName(catalog, null);

        System.out.println("\n[Test] Binary Search on empty array:");
        binarySearchById(new Product[0], 101);

        // ------------------------------------------------------------------
        // 5. Complexity summary
        // ------------------------------------------------------------------
        printSection("COMPLEXITY COMPARISON SUMMARY");
        System.out.printf("%-20s  %-18s  %-18s  %s%n",
                          "Algorithm", "Best Case", "Worst Case", "Space");
        System.out.println("-".repeat(65));
        System.out.printf("%-20s  %-18s  %-18s  %s%n",
                          "Linear Search", "O(1)", "O(n)", "O(1)");
        System.out.printf("%-20s  %-18s  %-18s  %s%n",
                          "Binary Search", "O(1)", "O(log n)", "O(1)");
        System.out.println("\nNote: Binary Search requires the array to be sorted – O(n log n).");
        System.out.println("      For one-time searches on unsorted data, Linear Search may be");
        System.out.println("      preferable because sorting overhead exceeds the search savings.");
        System.out.println("\n[Demo Complete]");
    }
}
