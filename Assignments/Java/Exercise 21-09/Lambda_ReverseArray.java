import java.util.Arrays;
import java.util.function.Function;

public class Lambda_ReverseArray {

    public static void main(String[] args) {

        int[] numbers = {10, 20, 30, 40, 50};

        // Lambda expression to reverse the integer array
        Function<int[], int[]> reverse = (array) -> {

            int[] reversed = new int[array.length];

            for (int i = 0; i < array.length; i++) {
                reversed[i] = array[array.length - 1 - i];
            }

            return reversed;
        };

        int[] result = reverse.apply(numbers);

        System.out.println("Original Array:");
        System.out.println(Arrays.toString(numbers));

        System.out.println("Reversed Array:");
        System.out.println(Arrays.toString(result));
    }
}