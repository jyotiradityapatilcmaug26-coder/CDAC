import java.util.Arrays;

public class Lambda_StringSort {

    public static void main(String[] args) {

        String[] names = {
            "Rahul",
            "Amit",
            "Sneha",
            "Priya",
            "Karan"
        };

        // Lambda expression to sort strings alphabetically
        Arrays.sort(names, (a, b) -> a.compareTo(b));

        System.out.println("Sorted Array:");

        for (String name : names) {
            System.out.println(name);
        }
    }
}