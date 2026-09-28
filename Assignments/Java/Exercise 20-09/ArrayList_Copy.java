import java.util.ArrayList;
import java.util.Collections;

public class ArrayList_Copy {

    public static void main(String[] args) {

        // Create the first ArrayList
        ArrayList<String> colors1 = new ArrayList<>();

        colors1.add("Red");
        colors1.add("Blue");
        colors1.add("Green");
        colors1.add("Yellow");

        // Create the second ArrayList
        ArrayList<String> colors2 = new ArrayList<>();

        // Make the second ArrayList large enough
        // to hold all elements
        colors2.add("");
        colors2.add("");
        colors2.add("");
        colors2.add("");

        // Copy elements from colors1 to colors2
        Collections.copy(colors2, colors1);

        System.out.println("First ArrayList:");
        System.out.println(colors1);

        System.out.println("Second ArrayList after copying:");
        System.out.println(colors2);
    }
}