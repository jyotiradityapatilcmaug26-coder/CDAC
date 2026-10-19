
public class BrokerageApp {

    /**
     * Immutable result object representing a single buy-sell trade.
     */
    public static class Trade {
        public final int buyDay;   // 1-based day
        public final int sellDay;  // 1-based day
        public final long profit;

        public Trade(int buyDay, int sellDay, long profit) {
            this.buyDay = buyDay;
            this.sellDay = sellDay;
            this.profit = profit;
        }

        /**
         * Formats the trade report to match the PDF example specification:
         * "Buy on day X at P_buy, sell on day Y at P_sell, profit = Z"
         */
        public String formatWithPrices(long[] prices) {
            if (profit <= 0 || prices == null || buyDay <= 0 || sellDay <= 0) {
                return "No profitable trade this month (profit = 0)";
            }
            long buyPrice = prices[buyDay - 1];
            long sellPrice = prices[sellDay - 1];
            return "Buy on day " + buyDay + " at " + buyPrice 
                 + ", sell on day " + sellDay + " at " + sellPrice 
                 + ", profit = " + profit;
        }

        @Override
        public String toString() {
            if (profit <= 0) {
                return "No profitable trade this month (profit = 0)";
            }
            return "Buy on day " + buyDay + ", sell on day " + sellDay + ", profit = " + profit;
        }

        public boolean matches(Trade other) {
            if (other == null) return false;
            return this.buyDay == other.buyDay 
                && this.sellDay == other.sellDay 
                && this.profit == other.profit;
        }
    }

    /**
     * Task 1: Brute-Force Solution - O(n^2) Time, O(1) Space
     */
    public static Trade findBestTradeBruteForce(long[] prices) {
        if (prices == null || prices.length < 2) {
            return new Trade(0, 0, 0);
        }

        int n = prices.length;
        long maxProfit = 0;
        int bestBuyDay = 0;
        int bestSellDay = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                long profit = prices[j] - prices[i];
                if (profit > maxProfit) {
                    maxProfit = profit;
                    bestBuyDay = i + 1;
                    bestSellDay = j + 1;
                }
            }
        }

        return new Trade(bestBuyDay, bestSellDay, maxProfit);
    }

    /**
     * Tasks 2 & 4: Single-Pass Solution - O(n) Time, O(1) Space
     * Remembers the minimum price seen so far and checks profit at each subsequent day.
     */
    public static Trade findBestTradeSinglePass(long[] prices) {
        if (prices == null || prices.length < 2) {
            return new Trade(0, 0, 0);
        }

        int n = prices.length;
        long minPriceSoFar = prices[0];
        int minDaySoFar = 1;

        long maxProfit = 0;
        int bestBuyDay = 0;
        int bestSellDay = 0;

        for (int i = 1; i < n; i++) {
            long currentPrice = prices[i];
            int currentDay = i + 1;

            if (currentPrice < minPriceSoFar) {
                // Better buy day found for future trades
                minPriceSoFar = currentPrice;
                minDaySoFar = currentDay;
            } else {
                // Calculate profit if sold today using the best prior buy day
                long profit = currentPrice - minPriceSoFar;
                if (profit > maxProfit) {
                    maxProfit = profit;
                    bestBuyDay = minDaySoFar;
                    bestSellDay = currentDay;
                }
            }
        }

        return new Trade(bestBuyDay, bestSellDay, maxProfit);
    }

    /**
     * Task 3: Prints the step-by-step dry run showing the remembered minimum price
     * and the best profit so far after every trading day.
     */
    public static void printDayByDayDryRun(long[] prices) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(" Task 3: Day-by-Day Dry Run for Example 1: [7, 1, 5, 3, 6, 4]                            ");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-6s | %-6s | %-24s | %-16s | %-22s%n", 
                          "Day", "Price", "Min Price So Far (Day)", "Today's Profit", "Best Profit So Far");
        System.out.println("-----------------------------------------------------------------------------------------");

        long minPriceSoFar = prices[0];
        int minDaySoFar = 1;
        long maxProfit = 0;
        int bestBuyDay = 0;
        int bestSellDay = 0;

        System.out.printf("Day %-2d | %-6d | %-24s | %-16s | %-22s%n", 
                          1, prices[0], minPriceSoFar + " (Day 1)", "-", "0 (No trade)");

        for (int i = 1; i < prices.length; i++) {
            long price = prices[i];
            int day = i + 1;
            String profitStr;

            if (price < minPriceSoFar) {
                minPriceSoFar = price;
                minDaySoFar = day;
                profitStr = "New min price";
            } else {
                long profit = price - minPriceSoFar;
                profitStr = price + " - " + minPriceSoFar + " = " + profit;
                if (profit > maxProfit) {
                    maxProfit = profit;
                    bestBuyDay = minDaySoFar;
                    bestSellDay = day;
                }
            }

            String bestStr = (maxProfit > 0) 
                    ? maxProfit + " (Buy D" + bestBuyDay + ", Sell D" + bestSellDay + ")" 
                    : "0";

            System.out.printf("Day %-2d | %-6d | %-24s | %-16s | %-22s%n", 
                              day, price, minPriceSoFar + " (Day " + minDaySoFar + ")", profitStr, bestStr);
        }
        System.out.println("-----------------------------------------------------------------------------------------\n");
    }

    /**
     * Task 5: Extension - Multiple Buy-Sell Trades
     * Time Complexity: O(n)
     * Space Complexity: O(1) auxiliary variables + O(n) for the transaction summary string.
     */
    public static class MultiTradeReport {
        public final long totalProfit;
        public final String transactionsSummary;

        public MultiTradeReport(long totalProfit, String transactionsSummary) {
            this.totalProfit = totalProfit;
            this.transactionsSummary = transactionsSummary;
        }

        @Override
        public String toString() {
            return "Total Profit = " + totalProfit + " (" + transactionsSummary + ")";
        }
    }

    public static MultiTradeReport findMaxProfitMultipleTrades(long[] prices) {
        if (prices == null || prices.length < 2) {
            return new MultiTradeReport(0, "No trades");
        }

        long totalProfit = 0;
        StringBuilder sb = new StringBuilder();
        int n = prices.length;

        int i = 0;
        while (i < n - 1) {
            // Find local valley (buy day)
            while (i < n - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            if (i >= n - 1) break;
            int buyDay = i + 1;
            long buyPrice = prices[i];

            // Find local peak (sell day)
            while (i < n - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            int sellDay = i + 1;
            long sellPrice = prices[i];

            long tradeProfit = sellPrice - buyPrice;
            totalProfit += tradeProfit;

            if (sb.length() > 0) sb.append(", then ");
            sb.append("buy at ").append(buyPrice).append(" (day ").append(buyDay).append(")")
              .append(", sell at ").append(sellPrice).append(" (day ").append(sellDay).append(")");
        }

        if (totalProfit == 0) {
            return new MultiTradeReport(0, "No profitable trades possible");
        }

        return new MultiTradeReport(totalProfit, sb.toString());
    }

    // ==========================================
    // MAIN EXECUTION & TESTS
    // ==========================================
    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("        QUESTION 2: BROKERAGE APP - BEST TRADE REPORT            ");
        System.out.println("==================================================================\n");

        // 1. Day-by-Day Dry Run for Example 1
        long[] ex1 = {7, 1, 5, 3, 6, 4};
        printDayByDayDryRun(ex1);

        // 2. Verified Execution on Example 1 (with Buy/Sell Prices in Output)
        System.out.println("[Example 1 Verification]");
        System.out.println("Input: prices = [7, 1, 5, 3, 6, 4]");
        Trade t1 = findBestTradeSinglePass(ex1);
        System.out.println("Output: " + t1.formatWithPrices(ex1));
        System.out.println("Expected: Buy on day 2 at 1, sell on day 5 at 6, profit = 5\n");

        // 3. Verified Execution on Example 2 (Prices Strictly Fall)
        long[] ex2 = {9, 7, 4, 3, 1};
        System.out.println("[Example 2 Verification]");
        System.out.println("Input: prices = [9, 7, 4, 3, 1]");
        Trade t2 = findBestTradeSinglePass(ex2);
        System.out.println("Output: " + t2.formatWithPrices(ex2));
        System.out.println("Expected: No profitable trade this month (profit = 0)\n");

        // 4. Task 5: Extension Multi-Trade Test
        System.out.println("[Task 5: Multiple Trades Extension]");
        MultiTradeReport multiReport = findMaxProfitMultipleTrades(ex1);
        System.out.println("Result: " + multiReport);
        System.out.println("Expected: Total Profit = 7 (buy at 1, sell at 5, then buy at 3, sell at 6)\n");

        // 5. Edge Cases Tested Side-by-Side
        runEdgeCases();
    }

    private static void runEdgeCases() {
        System.out.println("------------------------------------------------------------------");
        System.out.println(" Edge Cases: Testing Both Methods Side-by-Side                    ");
        System.out.println("------------------------------------------------------------------");

        // Case 1: Strictly falling prices
        verifyEdgeCase("Prices only fall", new long[]{10, 8, 6, 4, 2});

        // Case 2: All prices equal
        verifyEdgeCase("All prices equal", new long[]{5, 5, 5, 5, 5});

        // Case 3: Single day
        verifyEdgeCase("Single day", new long[]{42});

        // Case 4: Best buy day comes after a lower price that has no later high
        // Lowest price overall is 1 at day 4, but after day 4 highest is 3 (profit = 2).
        // Before day 4, buy at 2 (day 2) and sell at 8 (day 3) yields profit = 6!
        verifyEdgeCase("Low price with no later high", new long[]{10, 2, 8, 1, 3});

        // Case 5: Empty array
        verifyEdgeCase("Empty array", new long[]{});

        // Case 6: Null array
        verifyEdgeCase("Null array", null);

        System.out.println("==================================================================");
    }

    private static void verifyEdgeCase(String testName, long[] prices) {
        Trade bf = findBestTradeBruteForce(prices);
        Trade opt = findBestTradeSinglePass(prices);
        boolean match = bf.matches(opt);

        System.out.printf("Test [%-28s]: %s%n", testName, match ? "PASSED (Identical)" : "FAILED");
        System.out.println("   Output: " + opt.formatWithPrices(prices));
    }
}

/**

 * Conceptual Notes:
 * 1. Single-Pass Approach:
 *    - To find the best trade in a single pass without looking back, we remember
 *      the MINIMUM price seen so far and calculate the possible profit at each later price.
 *    - If today's price is smaller than minPriceSoFar, we update minPriceSoFar (a better buy opportunity).
 *    - If today's price minus minPriceSoFar exceeds maxProfit, we record a new best trade.
 *    - Time: O(n) | Auxiliary Space: O(1).
 * 
 * 2. Multiple-Trade Extension (Task 5) Complexity:
 *    - Time Complexity: O(n) — single pass scanning through valleys and peaks.
 *    - Space Complexity: The core mathematical profit accumulation uses O(1) space.
 *      However, storing the string description of all transactions in StringBuilder
 *      scales with the number of transactions, taking O(n) space in the worst case
 *      (e.g., alternating prices with up to n / 2 buy-sell pairs).
 */