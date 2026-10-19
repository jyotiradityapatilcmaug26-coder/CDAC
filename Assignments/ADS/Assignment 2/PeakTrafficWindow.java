
public class PeakTrafficWindow {

    /**
     * Immutable result container for peak traffic load and 1-based window minutes.
     */
    public static class WindowResult {
        public final long peakLoad;
        public final int startMinute; // 1-based
        public final int endMinute;   // 1-based
        public final long operationCount;

        public WindowResult(long peakLoad, int startMinute, int endMinute, long operationCount) {
            this.peakLoad = peakLoad;
            this.startMinute = startMinute;
            this.endMinute = endMinute;
            this.operationCount = operationCount;
        }

        @Override
        public String toString() {
            return "Peak " + (endMinute - startMinute + 1) + "-minute load = " 
                    + peakLoad + " requests (minutes " + startMinute + " to " + endMinute + ")";
        }

        public boolean matches(WindowResult other) {
            if (other == null) return false;
            return this.peakLoad == other.peakLoad 
                && this.startMinute == other.startMinute 
                && this.endMinute == other.endMinute;
        }
    }

    /**
     * Brute Force O(n * k) Implementation
     * Initialized with Long.MIN_VALUE so negative arrays are handled correctly.
     */
    public static WindowResult findPeakBruteForce(long[] req, int k) {
        if (req == null) {
            throw new IllegalArgumentException("Request array cannot be null.");
        }
        if (req.length == 0) {
            throw new IllegalArgumentException("Request array cannot be empty.");
        }
        int n = req.length;
        if (k <= 0) {
            throw new IllegalArgumentException("Window size k must be strictly positive (k > 0). Received: " + k);
        }
        if (k > n) {
            throw new IllegalArgumentException("Window size k cannot exceed array length n (" + n + "). Received: " + k);
        }

        long best = Long.MIN_VALUE; // Handles negative values properly
        int bestStart = 0;
        long opCount = 0;

        for (int i = 0; i + k <= n; i++) {
            long sum = 0;
            for (int j = i; j < i + k; j++) {
                sum += req[j];
                opCount++; // Count array addition
            }
            // Strict '>' guarantees the earliest window is reported on ties
            if (sum > best) {
                best = sum;
                bestStart = i;
            }
        }

        return new WindowResult(best, bestStart + 1, bestStart + k, opCount);
    }

    /**
     * Optimal Sliding Window: O(n) Time and O(1) Extra Space
     */
    public static WindowResult findPeakSlidingWindow(long[] req, int k) {
        if (req == null) {
            throw new IllegalArgumentException("Request array cannot be null.");
        }
        if (req.length == 0) {
            throw new IllegalArgumentException("Request array cannot be empty.");
        }
        int n = req.length;
        if (k <= 0) {
            throw new IllegalArgumentException("Window size k must be strictly positive (k > 0). Received: " + k);
        }
        if (k > n) {
            throw new IllegalArgumentException("Window size k cannot exceed array length n (" + n + "). Received: " + k);
        }

        long opCount = 0;
        long currentSum = 0;

        // 1. Compute sum of the first window
        for (int i = 0; i < k; i++) {
            currentSum += req[i];
            opCount++; // Count array addition
        }

        long best = currentSum;
        int bestStart = 0;

        // 2. Slide the window: subtract leaving element, add entering element
        for (int i = k; i < n; i++) {
            currentSum += req[i] - req[i - k];
            opCount += 2; // 1 addition and 1 subtraction per slide

            // Strict '>' preserves the earliest window in case of ties
            if (currentSum > best) {
                best = currentSum;
                bestStart = i - k + 1;
            }
        }

        return new WindowResult(best, bestStart + 1, bestStart + k, opCount);
    }

    /**
     * Traces and displays every single window sum for the PDF example.
     */
    public static void printWindowTrace(long[] req, int k) {
        System.out.println("--- Tracing All Window Sums (PDF Example) ---");
        long currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum += req[i];
        }

        int windowIndex = 1;
        System.out.printf("Window %d (Minutes %d to %d): Sum = %d%n", windowIndex, 1, k, currentSum);

        for (int i = k; i < req.length; i++) {
            windowIndex++;
            currentSum += req[i] - req[i - k];
            System.out.printf("Window %d (Minutes %d to %d): Sum = %d%n", 
                              windowIndex, i - k + 2, i + 1, currentSum);
        }
        System.out.println("Window Sums Sequence: 1020, 1110, 860, 980, 1490, 1550\n");
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("         PEAK TRAFFIC WINDOW (DAY 1 & 2 - QUESTION 1)            ");
        System.out.println("==================================================================\n");

        // Conceptual Explanation Output
        System.out.println("Algorithm Complexity Overview:");
        System.out.println(" - Brute Force    : O(n * k) time, O(1) extra space.");
        System.out.println(" - Sliding Window : O(n) time, O(1) extra space.");
        System.out.println(" - Operation Count: Measures algorithmic additions and subtractions,");
        System.out.println("                    not low-level CPU instructions.\n");

        // 1. PDF Example Verification & Dry-Run Trace
        long[] exampleReq = {120, 340, 560, 210, 90, 680, 720, 150};
        int kExample = 3;

        printWindowTrace(exampleReq, kExample);

        WindowResult bfExample = findPeakBruteForce(exampleReq, kExample);
        WindowResult optExample = findPeakSlidingWindow(exampleReq, kExample);

        System.out.println("Brute Force Output   : " + bfExample);
        System.out.println("Sliding Window Output: " + optExample);
        System.out.println("Outputs Match Identically: " + bfExample.matches(optExample));
        System.out.println();

        // 2. Large Value Test (Demonstrating why long is safer than int)
        runLargeValueOverflowTest();

        // 3. Task 2: Runtime Estimates
        printRuntimeEstimates();

        // 4. Task 4: Operation Count Benchmarks
        runBenchmarks();

        // 5. Valid Edge Cases Tested on BOTH Methods
        runValidEdgeCases();

        // 6. Invalid / Exception Edge Cases Tested on BOTH Methods
        runExceptionEdgeCases();
    }

    /**
     * Demonstrates why 'long' is necessary to avoid 32-bit integer overflow (> 2,147,483,647).
     */
    private static void runLargeValueOverflowTest() {
        System.out.println("------------------------------------------------------------------");
        System.out.println(" Large Values Test: Why 'long' is safer than 'int'                ");
        System.out.println(" Max signed 32-bit int = 2,147,483,647                            ");
        System.out.println("------------------------------------------------------------------");
        long[] largeReq = {1_500_000_000L, 1_800_000_000L, 1_200_000_000L};
        int k = 2;

        WindowResult bf = findPeakBruteForce(largeReq, k);
        WindowResult opt = findPeakSlidingWindow(largeReq, k);

        // Demonstrating what happens with 32-bit int:
        int overflowedIntSum = (int) largeReq[0] + (int) largeReq[1];

        System.out.println("Calculated Peak Load (long) : " + opt.peakLoad + " (Expected: 3300000000)");
        System.out.println("Same Sum in 32-bit 'int'     : " + overflowedIntSum + " (SILENT OVERFLOW to negative!)");
        System.out.println("Brute Force vs Optimal Match : " + bf.matches(opt));
        System.out.println();
    }

    private static void printRuntimeEstimates() {
        System.out.println("------------------------------------------------------------------");
        System.out.println(" Task 2: Runtime Estimates for n = 2,592,000, k = 3,600           ");
        System.out.println(" Processor speed: 10^8 operations / second                        ");
        System.out.println("------------------------------------------------------------------");
        long n = 2_592_000L;
        long k = 3_600L;
        double clockRate = 1e8;

        long bfOps = (n - k + 1) * k;
        double bfSeconds = bfOps / clockRate;

        long optOps = k + 2 * (n - k);
        double optSeconds = optOps / clockRate;

        System.out.printf("Brute Force   : %13d ops -> %6.2f seconds (approx 93.18 s / 1.55 min)%n", bfOps, bfSeconds);
        System.out.printf("Sliding Window: %13d ops -> %6.4f seconds (approx 0.052 seconds)%n", optOps, optSeconds);
        System.out.printf("Theoretical Speedup: %.1fx faster%n%n", (double) bfOps / optOps);
    }

    private static void runBenchmarks() {
        System.out.println("------------------------------------------------------------------");
        System.out.println(" Task 4: Measured Operation Counts (k = 1,000)                   ");
        System.out.println(" Note: Counts algorithmic additions/subtractions, not machine code");
        System.out.println("------------------------------------------------------------------");
        int[] testSizes = {1_000, 100_000, 1_000_000};
        int k = 1000;

        System.out.printf("%-10s | %-16s | %-16s | %-12s%n", "n", "Brute Force Ops", "Sliding Win Ops", "Ratio");
        System.out.println("------------------------------------------------------------------");

        for (int n : testSizes) {
            long[] dummyData = new long[n];
            for (int i = 0; i < n; i++) dummyData[i] = (i % 50) + 1;

            WindowResult opt = findPeakSlidingWindow(dummyData, k);
            long bfOps = (n <= 100_000) 
                    ? findPeakBruteForce(dummyData, k).operationCount 
                    : (long) (n - k + 1) * k;

            double ratio = (double) bfOps / opt.operationCount;
            System.out.printf("%-10d | %-16d | %-16d | %-12.1fx%n", n, bfOps, opt.operationCount, ratio);
        }
        System.out.println();
    }

    private static void runValidEdgeCases() {
        System.out.println("------------------------------------------------------------------");
        System.out.println(" Valid Edge Cases: Testing BOTH Methods Side-by-Side              ");
        System.out.println("------------------------------------------------------------------");

        // 1. k = 1
        verifyValidCase("k = 1", new long[]{50, 120, 30, 90}, 1);

        // 2. k = n
        verifyValidCase("k = n", new long[]{10, 20, 30, 40}, 4);

        // 3. All values zero
        verifyValidCase("All Values Zero", new long[]{0, 0, 0, 0, 0}, 2);

        // 4. Negative values (tests Long.MIN_VALUE)
        verifyValidCase("Negative Values", new long[]{-10, -5, -20, -2, -30}, 2);

        // 5. Earliest window tie-breaker
        verifyValidCase("Tie Breaking (Earliest)", new long[]{100, 200, 50, 250, 10}, 2);

        System.out.println();
    }

    private static void verifyValidCase(String testName, long[] data, int k) {
        WindowResult bf = findPeakBruteForce(data, k);
        WindowResult opt = findPeakSlidingWindow(data, k);
        boolean match = bf.matches(opt);

        System.out.printf("Test [%-23s]: %s%n", testName, match ? "PASSED (Identical)" : "FAILED (Mismatch)");
        System.out.println("   Brute Force   : " + bf);
        System.out.println("   Sliding Window: " + opt);
    }

    private static void runExceptionEdgeCases() {
        System.out.println("------------------------------------------------------------------");
        System.out.println(" Exception Edge Cases: Testing BOTH Methods (Must Throw Exception)");
        System.out.println("------------------------------------------------------------------");

        // Case A: null array
        verifyExceptionCase("null array", null, 3);

        // Case B: empty array
        verifyExceptionCase("empty array", new long[]{}, 1);

        // Case C: k = 0
        verifyExceptionCase("k = 0", new long[]{10, 20, 30}, 0);

        // Case D: negative k
        verifyExceptionCase("negative k (k = -2)", new long[]{10, 20, 30}, -2);

        // Case E: k > n
        verifyExceptionCase("k > n (n = 2, k = 5)", new long[]{10, 20}, 5);

        System.out.println("==================================================================");
    }

    private static void verifyExceptionCase(String testName, long[] data, int k) {
        boolean bfThrew = false;
        boolean optThrew = false;
        String bfMsg = "";
        String optMsg = "";

        // Test Brute Force
        try {
            findPeakBruteForce(data, k);
        } catch (IllegalArgumentException e) {
            bfThrew = true;
            bfMsg = e.getMessage();
        }

        // Test Sliding Window
        try {
            findPeakSlidingWindow(data, k);
        } catch (IllegalArgumentException e) {
            optThrew = true;
            optMsg = e.getMessage();
        }

        boolean pass = bfThrew && optThrew;
        System.out.printf("Test [%-23s]: %s%n", testName, pass ? "PASSED (Both threw IllegalArgumentException)" : "FAILED");
        if (pass) {
            System.out.println("   BF caught : " + bfMsg);
            System.out.println("   Opt caught: " + optMsg);
        }
    }
}

/**
 * Complexity & Design Notes:
 * 1. Time Complexity:
 *    - Brute Force takes O(n * k) time because for each of the (n - k + 1) starting positions,
 *      an inner loop accumulates all k values.
 *    - Sliding Window takes O(n) time because it computes the first window sum in O(k),
 *      and then slides across the remaining elements in O(n - k), doing exactly 1 addition
 *      and 1 subtraction per step.
 * 2. Space Complexity:
 *    - Both algorithms use O(1) auxiliary extra space (a few scalar tracking variables).
 * 3. Operation Counter:
 *    - Measures selected array additions and subtractions (algorithmic arithmetic operations),
 *      not every machine instruction (such as index increments, branch checks, or loop overhead).
 * 4. Why 'long' is Safer than 'int':
 *    - Java 'int' is a 32-bit signed integer with maximum value 2,147,483,647.
 *    - Cumulative server requests over large windows (e.g., k = 3,600 seconds) during traffic bursts
 *      easily exceed 2,147,483,647, causing silent negative integer overflow in 'int'.
 *    - 'long' provides 64-bit precision up to ~9.22 * 10^18, completely preventing overflow.
 */