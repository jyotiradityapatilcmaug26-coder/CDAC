import java.util.ArrayList;

public class ArrayList_Update {

    public static void main(String[] args) {

        // Create an ArrayList
        ArrayList<String> colors = new ArrayList<>();

        // Add elements
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");

        System.out.println("Before update: " + colors);

        // Update the element at index 2
        colors.set(2, "Orange");

        System.out.println("After update: " + colors);
    }
}