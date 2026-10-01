import java.util.Arrays;
import java.util.function.BinaryOperator;

public class Lambda_SmallestNumber {

    public static void main(String[] args) {

        int[] numbers = {10, 25, 5, 40, 15};

        // Lambda expression to compare two numbers
        BinaryOperator<Integer> smallest =
                (a, b) -> a < b ? a : b;

        // Find smallest number
        int result = Arrays.stream(numbers)
                           .boxed()
                           .reduce(smallest)
                           .get();

        System.out.println("Smallest Number: " + result);
    }
}