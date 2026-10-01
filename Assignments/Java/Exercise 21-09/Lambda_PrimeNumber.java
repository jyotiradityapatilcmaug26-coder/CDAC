import java.util.function.Predicate;

public class Lambda_PrimeNumber {

    public static void main(String[] args) {

        int number = 17;

        // Lambda expression to check whether number is prime
        Predicate<Integer> isPrime = (n) -> {

            if (n < 2) {
                return false;
            }

            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) {
                    return false;
                }
            }

            return true;
        };

        System.out.println("Number: " + number);
        System.out.println("Is Prime: " + isPrime.test(number));
    }
}