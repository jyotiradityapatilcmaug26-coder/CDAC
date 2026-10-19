public class BankUtils {

    // 1. Validate account number length: counts digits recursively
    public static int countDigits(long n) {
        // Special case: Long.MIN_VALUE cannot be made positive by -n or Math.abs
        if (n == Long.MIN_VALUE) {
            return 1 + countDigits(-(n / 10));
        }
        if (n < 0) {
            return countDigits(-n);
        }
        // Base case: single digit (0 through 9)
        if (n < 10) {
            return 1;
        }
        // Recursive case
        return 1 + countDigits(n / 10);
    }

    // 2. Sum of digits: used in check-digit calculation
    public static int sumDigits(long n) {
        // Special case: Long.MIN_VALUE (-9223372036854775808)
        if (n == Long.MIN_VALUE) {
            return 8 + sumDigits(-(n / 10)); // Last digit is 8, recurse on remainder
        }
        if (n < 0) {
            return sumDigits(-n);
        }
        // Base case: single digit
        if (n < 10) {
            return (int) n;
        }
        // Recursive case
        return (int) (n % 10) + sumDigits(n / 10);
    }

    // 3. Digital root: keep summing digits until one digit is left
    public static int digitalRoot(long n) {
        int sum = sumDigits(n);
        // Base case: already a single digit
        if (sum < 10) {
            return sum;
        }
        // Recursive case
        return digitalRoot(sum);
    }

    // 4. Mask: hide all but the last 4 characters on statements
    public static String mask(String acc) {
        if (acc == null) {
            return "";
        }
        // Base case: length 4 or less, return unchanged
        if (acc.length() <= 4) {
            return acc;
        }
        // Recursive case: prepend 'X' and recurse on substring
        return "X" + mask(acc.substring(1));
    }

    // 5. Compound interest: amount after n years
    public static double amount(double p, double r, int years) {
        // Base case: 0 or negative years returns principal unchanged
        if (years <= 0) {
            return p;
        }
        // Recursive case: amount(n) = amount(n - 1) * (1 + r / 100)
        return amount(p, r, years - 1) * (1.0 + r / 100.0);
    }

    public static void main(String[] args) {
        System.out.println("--- Running Examples from PDF Table ---");

        long acc = 4096013L;
        System.out.println("countDigits(" + acc + ")  -> " + countDigits(acc));
        System.out.println("sumDigits(" + acc + ")    -> " + sumDigits(acc));
        System.out.println("digitalRoot(" + acc + ")  -> " + digitalRoot(acc));

        String accStr = "123456789012";
        System.out.println("mask(\"" + accStr + "\") -> \"" + mask(accStr) + "\"");

        double p = 10000;
        double r = 10;
        int years = 3;
        double totalAmt = amount(p, r, years);
        System.out.println("amount(" + (int)p + ", " + (int)r + ", " + years + ") -> " + String.format("%.2f", totalAmt));

        System.out.println("\n--- Testing Long.MIN_VALUE Handling ---");
        System.out.println("countDigits(Long.MIN_VALUE) -> " + countDigits(Long.MIN_VALUE));
        System.out.println("sumDigits(Long.MIN_VALUE)   -> " + sumDigits(Long.MIN_VALUE));
        System.out.println("digitalRoot(Long.MIN_VALUE) -> " + digitalRoot(Long.MIN_VALUE));

        System.out.println("\n--- Testing mask() Edge Cases ---");
        System.out.println("mask(null)     -> \"" + mask(null) + "\"");
        System.out.println("mask(\"\")       -> \"" + mask("") + "\"");
        System.out.println("mask(\"1234\")   -> \"" + mask("1234") + "\" (exactly 4 chars)");
        System.out.println("mask(\"12345\")  -> \"" + mask("12345") + "\" (exactly 5 chars)");

        System.out.println("\n--- Testing amount() Variations ---");
        System.out.println("amount(10000, 10, 0)  -> " + String.format("%.2f", amount(10000, 10, 0)) + " (zero years)");
        System.out.println("amount(10000, 10, -2) -> " + String.format("%.2f", amount(10000, 10, -2)) + " (negative years)");
        System.out.println("amount(10000, 0, 5)   -> " + String.format("%.2f", amount(10000, 0, 5)) + " (zero interest)");
        System.out.println("amount(10000, 5, 2)   -> " + String.format("%.2f", amount(10000, 5, 2)) + " (5% for 2 years)");
    }
}