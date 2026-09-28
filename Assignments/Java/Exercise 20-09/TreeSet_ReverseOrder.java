import java.util.TreeSet;

public class TreeSet_ReverseOrder {

    public static void main(String[] args) {

        // Create a TreeSet
        TreeSet<String> colors = new TreeSet<>();

        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");
        colors.add("Black");

        // Display TreeSet in normal order
        System.out.println("Normal Order:");
        System.out.println(colors);

        // Create reverse order view
        TreeSet<String> reverseColors =
                (TreeSet<String>) colors.descendingSet();

        // Display TreeSet in reverse order
        System.out.println("Reverse Order:");
        System.out.println(reverseColors);
    }
}