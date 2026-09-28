import java.util.TreeSet;

public class TreeSet_AddAll {

    public static void main(String[] args) {

        // Create first TreeSet
        TreeSet<String> colors1 = new TreeSet<>();

        colors1.add("Red");
        colors1.add("Blue");
        colors1.add("Green");

        // Create second TreeSet
        TreeSet<String> colors2 = new TreeSet<>();

        colors2.add("Yellow");
        colors2.add("Black");

        // Add all elements of colors1 into colors2
        colors2.addAll(colors1);

        // Display the second TreeSet
        System.out.println("TreeSet after adding all elements:");
        System.out.println(colors2);
    }
}