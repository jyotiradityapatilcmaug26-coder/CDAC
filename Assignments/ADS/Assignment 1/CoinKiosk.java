public class CoinKiosk {
    static long calls = 0;

    // (a) Recursively prints each valid coin sequence
    static void printWays(int amount, String coinsSoFar) {
        // Guard against negative amounts
        if (amount < 0) {
            return;
        }
        if (amount == 0) {
            System.out.println(" " + coinsSoFar.trim());
            return;
        }

        // Choice 1: Try inserting a Rs. 1 coin
        printWays(amount - 1, coinsSoFar + "1 ");

        // Choice 2: Try inserting a Rs. 2 coin
        printWays(amount - 2, coinsSoFar + "2 ");
    }

    // (b) Recursively counts the number of valid sequences (pure recursion)
    static long countWays(int amount) {
        calls++;
        // Guard against negative amounts
        if (amount < 0) {
            return 0;
        }
        // Base cases
        if (amount == 0 || amount == 1) {
            return 1;
        }
        return countWays(amount - 1) + countWays(amount - 2);
    }

    // Task 5: Memoized version to eliminate redundant recursive calculations
    static long countWaysMemo(int amount, long[] memo) {
        calls++;
        // Guard against negative amounts before accessing memo array
        if (amount < 0) {
            return 0;
        }
        if (amount == 0 || amount == 1) {
            return 1;
        }
        if (memo[amount] != 0) {
            return memo[amount];
        }
        memo[amount] = countWaysMemo(amount - 1, memo) + countWaysMemo(amount - 2, memo);
        return memo[amount];
    }

    // Task 6 Extension: Accepts Rs. 1, Rs. 2, and Rs. 5 coins (pure recursion)
    static long countWaysWith5(int amount) {
        if (amount < 0) return 0;
        if (amount == 0) return 1;

        return countWaysWith5(amount - 1) +
               countWaysWith5(amount - 2) +
               countWaysWith5(amount - 5);
    }

    // Extension: Memoized version of the 3-coin extension (Rs. 1, 2, 5) for high performance
    static long countWaysWith5Memo(int amount, long[] memo) {
        if (amount < 0) return 0;
        if (amount == 0) return 1;

        if (memo[amount] != 0) {
            return memo[amount];
        }
        memo[amount] = countWaysWith5Memo(amount - 1, memo) +
                       countWaysWith5Memo(amount - 2, memo) +
                       countWaysWith5Memo(amount - 5, memo);
        return memo[amount];
    }

    public static void main(String[] args) {
        System.out.println("--- Running Example from PDF ---");
        int amount = 4;
        System.out.println("Amount = " + amount);
        printWays(amount, "");

        calls = 0;
        long totalWays = countWays(amount);
        System.out.println("Total ways = " + totalWays);

        System.out.println();
        calls = 0;
        System.out.println("countWays(5)  = " + countWays(5));
        calls = 0;
        System.out.println("countWays(10) = " + countWays(10));
        calls = 0;
        System.out.println("countWays(30) = " + countWays(30));

        System.out.println("\n--- Task 3: Call Counts and Timings (Pure Recursion) ---");
        int[] testAmounts = {10, 20, 30, 40};
        System.out.printf("%-10s %-15s %-15s %-15s%n", "Amount", "Ways", "Calls", "Time (ms)");
        for (int a : testAmounts) {
            calls = 0; // Reset before each measurement
            long start = System.currentTimeMillis();
            long ways = countWays(a);
            long time = System.currentTimeMillis() - start;
            System.out.printf("%-10d %-15d %-15d %-15d%n", a, ways, calls, time);
        }

        System.out.println("\n--- Task 5: Memoization for Amount 40 ---");
        calls = 0;
        long[] memo = new long[41];
        long startMemo = System.currentTimeMillis();
        long memoWays40 = countWaysMemo(40, memo);
        long timeMemo = System.currentTimeMillis() - startMemo;
        System.out.println("Memoized countWaysMemo(40): " + memoWays40);
        System.out.println("Total recursive calls with memo: " + calls + " (took " + timeMemo + " ms)");

        System.out.println("\n--- Task 6 Extension (Rs. 1, 2, and 5 Coins) ---");
        System.out.println("countWaysWith5(5) = " + countWaysWith5(5) + " (Expected: 9)");
        long[] memo5 = new long[41];
        System.out.println("Memoized countWaysWith5Memo(40) = " + countWaysWith5Memo(40, memo5));

        System.out.println("\n--- Testing Negative Amount Guards ---");
        System.out.print("printWays(-3): ");
        printWays(-3, ""); // Completes safely without printing
        System.out.println("[Completed safely with no output]");
        System.out.println("countWays(-5)        = " + countWays(-5));
        System.out.println("countWaysMemo(-5)    = " + countWaysMemo(-5, new long[10]));
        System.out.println("countWaysWith5(-5)   = " + countWaysWith5(-5));
    }
}