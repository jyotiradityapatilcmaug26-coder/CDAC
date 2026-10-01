import java.util.ArrayList;

public class ArrayList_Remove {

    public static void main(String[] args) {

        // Create an ArrayList
        ArrayList<String> colors = new ArrayList<>();

        // Add elements
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");

        System.out.println("Before removing: " + colors);

        // Remove the third element
        // Third element has index 2
        colors.remove(2);

        System.out.println("After removing third element: " + colors);
    }
}