import java.util.ArrayList;

public class ArrayList_Retrieve {

    public static void main(String[] args) {

        // Create an ArrayList
        ArrayList<String> colors = new ArrayList<>();

        // Add elements
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");

        // Specify the index
        int index = 2;

        // Retrieve the element at the specified index
        String color = colors.get(index);

        System.out.println("Element at index " + index + ": " + color);
    }
}