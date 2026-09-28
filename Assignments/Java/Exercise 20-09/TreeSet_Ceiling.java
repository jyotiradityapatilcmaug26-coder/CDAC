import java.util.TreeSet;

public class TreeSet_Ceiling {

    public static void main(String[] args) {

        // Create a TreeSet
        TreeSet<Integer> numbers = new TreeSet<>();

        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        numbers.add(50);

        // Given element
        int element = 25;

        // Find element greater than or equal to the given element
        System.out.println("Given Element: " + element);
        System.out.println("Ceiling Element: " + numbers.ceiling(element));
    }
}