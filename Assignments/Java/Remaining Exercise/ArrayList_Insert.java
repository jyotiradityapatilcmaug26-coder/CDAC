import java.util.ArrayList;

public class ArrayList_Insert {

    public static void main(String[] args) {

        // Create an ArrayList
        ArrayList<String> colors = new ArrayList<>();

        // Add elements
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");

        System.out.println("Before inserting: " + colors);

        // Insert an element at the first position
        colors.add(0, "Yellow");

        System.out.println("After inserting at first position: " + colors);
    }
}