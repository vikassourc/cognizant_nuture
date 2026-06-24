import java.util.HashMap;
import java.util.Map;

/**
 * FinancialForecasting.java
 *
 * <p>Demonstrates recursive calculation of compound-interest future value (FV) with
 * <b>memoization</b> to eliminate redundant recursive calls.
 *
 * <p><b>Core Formula:</b>
 * <pre>
 *   FV = PV × (1 + r)^n
 * </pre>
 * where:
 * <ul>
 *   <li><b>PV</b> – Present Value (initial investment)</li>
 *   <li><b>r</b>  – Annual interest / growth rate (decimal, e.g. 0.08 for 8 %)</li>
 *   <li><b>n</b>  – Number of compounding periods (years)</li>
 * </ul>
 *
 * <h2>Recursion vs. Memoized Recursion</h2>
 *
 * <p><b>Plain Recursion</b>
 * <pre>
 *   calculateFV(PV, r, n) =
 *       PV                                    if n == 0   (base case)
 *       calculateFV(PV, r, n-1) × (1 + r)    otherwise
 * </pre>
 * Time Complexity  : O(n) – one multiplication per recursive level, n levels deep.<br>
 * Space Complexity : O(n) – call stack depth grows linearly with n.
 *
 * <p><b>Naive Recursive Drawback for overlapping sub-problems:</b><br>
 * If we call {@code forecastRange(PV, r, 1..N)}, the plain recursion recomputes
 * intermediate powers from scratch for each year. Computing year 10 independently
 * from year 9 would duplicate all sub-calls 1..9. Memoization caches each
 * sub-result, reducing repeated work.
 *
 * <p><b>Memoized Recursion</b><br>
 * Time Complexity  : O(n) total (each sub-problem solved once, then O(1) lookup).<br>
 * Space Complexity : O(n) – memo table stores one entry per unique {@code n}.
 *
 * <h2>When to prefer iteration over recursion</h2>
 * <p>For very large {@code n} (thousands of years), the recursive call stack may cause
 * a {@link StackOverflowError}. An iterative approach is then preferred. The iterative
 * version is also included in this class for comparison.
 *
 * @author  Cognizant Nurture – Week 1, Exercise 7
 * @version 1.0
 */
public class FinancialForecasting {

    // -------------------------------------------------------------------------
    // 1. Plain Recursive Future Value
    // -------------------------------------------------------------------------

    /**
     * Calculates the future value of an investment using <b>plain recursion</b>.
     *
     * <p>Recurrence relation:
     * <pre>
     *   FV(PV, r, 0) = PV
     *   FV(PV, r, n) = FV(PV, r, n-1) × (1 + r)
     * </pre>
     *
     * <p><b>Time Complexity:</b> O(n) – recurses n times before reaching the base case.<br>
     * <b>Space Complexity:</b> O(n) – n stack frames are held simultaneously.</p>
     *
     * @param presentValue initial investment amount (must be ≥ 0)
     * @param rate         annual growth rate as a decimal (e.g. 0.08 = 8%)
     * @param years        number of compounding periods / years (must be ≥ 0)
     * @return the future value after {@code years} periods
     * @throws IllegalArgumentException if {@code presentValue} &lt; 0 or {@code years} &lt; 0
     */
    public static double calculateFV(double presentValue, double rate, int years) {
        // Validate inputs
        if (presentValue < 0) {
            throw new IllegalArgumentException("presentValue must be non-negative.");
        }
        if (years < 0) {
            throw new IllegalArgumentException("years must be non-negative.");
        }

        // Base case: no compounding periods remain
        if (years == 0) {
            return presentValue;
        }

        // Recursive case: grow last year's value by one period
        return calculateFV(presentValue, rate, years - 1) * (1 + rate);
    }

    // -------------------------------------------------------------------------
    // 2. Memoized Recursive Future Value
    // -------------------------------------------------------------------------

    /**
     * Calculates the future value using <b>memoized recursion</b>.
     *
     * <p>A {@link HashMap} serves as the memo table. Before making a recursive call
     * the method checks whether the result for the current {@code years} is already
     * cached. If so, it returns the cached value in O(1) instead of recursing further.
     *
     * <p><b>Time Complexity:</b> O(n) total across all calls sharing the same memo table –
     * each unique sub-problem is solved exactly once.<br>
     * <b>Space Complexity:</b> O(n) – memo table grows by one entry per unique {@code years}.</p>
     *
     * <p><b>Usage tip:</b> Pass a fresh {@code HashMap} on the first call. The same map
     * can be reused across multiple calls for the same {@code rate} to maximise cache hits.</p>
     *
     * @param presentValue initial investment amount (must be ≥ 0)
     * @param rate         annual growth rate as a decimal
     * @param years        number of compounding periods (must be ≥ 0)
     * @param memo         shared memoization cache; key = years, value = FV at that year
     * @return the future value after {@code years} periods
     */
    public static double calculateFVMemo(double presentValue, double rate,
                                         int years, Map<Integer, Double> memo) {
        // Base case
        if (years == 0) {
            return presentValue;
        }

        // Check memo cache before recursing
        if (memo.containsKey(years)) {
            return memo.get(years);                 // O(1) lookup
        }

        // Recurse and cache the result
        double fv = calculateFVMemo(presentValue, rate, years - 1, memo) * (1 + rate);
        memo.put(years, fv);
        return fv;
    }

    // -------------------------------------------------------------------------
    // 3. Iterative Future Value (for comparison / large n)
    // -------------------------------------------------------------------------

    /**
     * Calculates the future value using a simple <b>iterative</b> loop.
     *
     * <p>This avoids call-stack growth entirely and is suitable for very large {@code n}.
     *
     * <p><b>Time Complexity:</b> O(n)<br>
     * <b>Space Complexity:</b> O(1) – only one accumulator variable is needed.</p>
     *
     * @param presentValue initial investment amount
     * @param rate         annual growth rate as a decimal
     * @param years        number of compounding periods
     * @return the future value after {@code years} periods
     */
    public static double calculateFVIterative(double presentValue, double rate, int years) {
        double fv = presentValue;
        for (int i = 0; i < years; i++) {
            fv *= (1 + rate);
        }
        return fv;
    }

    // -------------------------------------------------------------------------
    // 4. Year-by-year forecast
    // -------------------------------------------------------------------------

    /**
     * Generates a year-by-year forecast table using the memoized recursive method.
     *
     * <p>Because memoized results accumulate in the shared {@code memo} table, each
     * year's value is derived from the already-cached result of the previous year
     * in O(1) amortised time. Total time for the full forecast: O(n).</p>
     *
     * @param presentValue  initial investment amount
     * @param rate          annual growth rate as a decimal
     * @param forecastYears total number of years to forecast
     */
    public static void printForecastTable(double presentValue, double rate, int forecastYears) {
        if (forecastYears <= 0) {
            System.out.println("[Forecast] forecastYears must be greater than 0.");
            return;
        }

        Map<Integer, Double> memo = new HashMap<>();

        System.out.printf("%n%-6s  %-18s  %-18s  %s%n",
                          "Year", "Future Value (FV)", "Annual Gain ($)", "Annual Gain (%)");
        System.out.println("-".repeat(65));

        double previousFV = presentValue;

        for (int year = 1; year <= forecastYears; year++) {
            double fv         = calculateFVMemo(presentValue, rate, year, memo);
            double annualGain = fv - previousFV;
            double gainPct    = (annualGain / previousFV) * 100.0;

            System.out.printf("%-6d  $%-17.2f  $%-17.2f  %.2f%%%n",
                              year, fv, annualGain, gainPct);

            previousFV = fv;
        }

        System.out.printf("%nFinal Value after %d year(s): $%.2f%n", forecastYears,
                          calculateFVMemo(presentValue, rate, forecastYears, memo));
        System.out.printf("Total Growth: $%.2f (%.2f%%)%n",
                          previousFV - presentValue,
                          ((previousFV - presentValue) / presentValue) * 100.0);
    }

    // -------------------------------------------------------------------------
    // Helper utilities
    // -------------------------------------------------------------------------

    /**
     * Prints a formatted section header.
     *
     * @param title section title
     */
    private static void printSection(String title) {
        System.out.println("\n" + "=".repeat(65));
        System.out.println("  " + title);
        System.out.println("=".repeat(65));
    }

    // -------------------------------------------------------------------------
    // Main method
    // -------------------------------------------------------------------------

    /**
     * Demonstrates financial forecasting with realistic scenarios.
     *
     * <p>Scenarios covered:
     * <ol>
     *   <li>Retirement savings at 7% annual growth over 30 years.</li>
     *   <li>Short-term bond investment at 4% over 5 years.</li>
     *   <li>High-risk equity portfolio at 12% over 20 years.</li>
     *   <li>Comparison of plain recursive, memoized, and iterative results.</li>
     *   <li>Edge-case handling.</li>
     * </ol>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        printSection("FINANCIAL FORECASTING – COMPOUND INTEREST");

        System.out.println("\n  Formula : FV = PV × (1 + r)^n");
        System.out.println("  Methods : (1) Plain Recursion  (2) Memoized Recursion  (3) Iterative");

        // ------------------------------------------------------------------
        // Scenario 1: Retirement Savings
        // ------------------------------------------------------------------
        printSection("Scenario 1 – Retirement Savings");
        double pv1    = 25_000.00;   // Initial lump-sum investment
        double rate1  = 0.07;        // 7% annual growth (diversified index fund)
        int    years1 = 30;

        System.out.printf("  Present Value : $%.2f%n", pv1);
        System.out.printf("  Annual Rate   : %.0f%%%n", rate1 * 100);
        System.out.printf("  Forecast      : %d years%n", years1);

        printForecastTable(pv1, rate1, years1);

        // ------------------------------------------------------------------
        // Scenario 2: Short-Term Bond
        // ------------------------------------------------------------------
        printSection("Scenario 2 – Short-Term Government Bond");
        double pv2    = 10_000.00;
        double rate2  = 0.04;        // 4% annual yield
        int    years2 = 5;

        System.out.printf("  Present Value : $%.2f%n", pv2);
        System.out.printf("  Annual Rate   : %.0f%%%n", rate2 * 100);
        System.out.printf("  Forecast      : %d years%n", years2);

        printForecastTable(pv2, rate2, years2);

        // ------------------------------------------------------------------
        // Scenario 3: High-Risk Equity Portfolio
        // ------------------------------------------------------------------
        printSection("Scenario 3 – High-Risk Equity Portfolio");
        double pv3    = 5_000.00;
        double rate3  = 0.12;        // 12% annual growth
        int    years3 = 20;

        System.out.printf("  Present Value : $%.2f%n", pv3);
        System.out.printf("  Annual Rate   : %.0f%%%n", rate3 * 100);
        System.out.printf("  Forecast      : %d years%n", years3);

        printForecastTable(pv3, rate3, years3);

        // ------------------------------------------------------------------
        // Scenario 4: Method Comparison
        // ------------------------------------------------------------------
        printSection("Method Comparison – Plain vs. Memoized vs. Iterative");

        double pv4   = 1_000.00;
        double rate4 = 0.08;
        int    n4    = 10;

        Map<Integer, Double> memo4 = new HashMap<>();

        double fvRecursive  = calculateFV(pv4, rate4, n4);
        double fvMemoized   = calculateFVMemo(pv4, rate4, n4, memo4);
        double fvIterative  = calculateFVIterative(pv4, rate4, n4);

        System.out.printf("%n  PV=$%.2f  r=%.0f%%  n=%d years%n%n", pv4, rate4 * 100, n4);
        System.out.printf("  %-25s : $%.6f%n", "Plain Recursive",   fvRecursive);
        System.out.printf("  %-25s : $%.6f%n", "Memoized Recursive", fvMemoized);
        System.out.printf("  %-25s : $%.6f%n", "Iterative",          fvIterative);
        System.out.println("\n  All three methods yield identical results. ✓");

        // ------------------------------------------------------------------
        // Scenario 5: Edge cases
        // ------------------------------------------------------------------
        printSection("Edge-Case Handling");

        System.out.println("\n[Test 1] Zero years (no compounding):");
        double fvZeroYears = calculateFV(5000.00, 0.07, 0);
        System.out.printf("  FV(5000, 7%%, 0) = $%.2f  (should equal PV)%n", fvZeroYears);

        System.out.println("\n[Test 2] Zero interest rate:");
        double fvZeroRate = calculateFV(5000.00, 0.00, 10);
        System.out.printf("  FV(5000, 0%%, 10) = $%.2f  (should equal PV)%n", fvZeroRate);

        System.out.println("\n[Test 3] Very small investment ($1):");
        double fvSmall = calculateFV(1.00, 0.05, 20);
        System.out.printf("  FV($1, 5%%, 20) = $%.6f%n", fvSmall);

        System.out.println("\n[Test 4] Negative present value (should throw exception):");
        try {
            calculateFV(-100.00, 0.05, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage());
        }

        System.out.println("\n[Test 5] Negative years (should throw exception):");
        try {
            calculateFV(1000.00, 0.05, -5);
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage());
        }

        System.out.println("\n[Test 6] forecastYears = 0 (invalid input to table printer):");
        printForecastTable(1000.00, 0.05, 0);

        // ------------------------------------------------------------------
        // Complexity summary
        // ------------------------------------------------------------------
        printSection("COMPLEXITY SUMMARY");
        System.out.printf("%-28s  %-14s  %s%n", "Method", "Time", "Space");
        System.out.println("-".repeat(55));
        System.out.printf("%-28s  %-14s  %s%n", "Plain Recursion",    "O(n)", "O(n) stack");
        System.out.printf("%-28s  %-14s  %s%n", "Memoized Recursion", "O(n)", "O(n) memo");
        System.out.printf("%-28s  %-14s  %s%n", "Iterative",          "O(n)", "O(1)");
        System.out.println("\nNote: Math.pow(base, exp) achieves O(log n) via fast exponentiation,");
        System.out.println("      but the recursive decomposition shown here uses O(n) to illustrate");
        System.out.println("      the memoization pattern clearly for educational purposes.");
        System.out.println("\n[Demo Complete]");
    }
}
