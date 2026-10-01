import java.util.Arrays;

public class Lambda_LargestNumber {

    public static void main(String[] args) {

        int[] numbers = {10, 25, 5, 40, 15};

        // Lambda expression to compare two numbers
        java.util.function.BinaryOperator<Integer> largest =
                (a, b) -> a > b ? a : b;

        // Find largest number
        int result = Arrays.stream(numbers)
                           .boxed()
                           .reduce(largest)
                           .get();

        System.out.println("Largest Number: " + result);
    }
}