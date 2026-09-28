import java.util.TreeSet;

public class TreeSet_FirstLast {

    public static void main(String[] args) {

        // Create a TreeSet
        TreeSet<String> colors = new TreeSet<>();

        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");
        colors.add("Black");

        // Get the first element
        System.out.println("First Element:");
        System.out.println(colors.first());

        // Get the last element
        System.out.println("Last Element:");
        System.out.println(colors.last());
    }
}