import java.util.ArrayList;

public class ArrayList_Search {

    public static void main(String[] args) {

        // Create an ArrayList
        ArrayList<String> colors = new ArrayList<>();

        // Add elements
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");

        // Element to search
        String searchColor = "Green";

        // Search for the element
        if (colors.contains(searchColor)) {
            System.out.println(searchColor + " is present in the ArrayList.");
        } else {
            System.out.println(searchColor + " is not present in the ArrayList.");
        }
    }
}