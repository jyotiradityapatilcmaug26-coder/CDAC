import java.util.ArrayList;
import java.util.Collections;

public class ArrayList_Shuffle {

    public static void main(String[] args) {

        // Create an ArrayList
        ArrayList<String> colors = new ArrayList<>();

        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");
        colors.add("Black");

        // Display ArrayList before shuffling
        System.out.println("Before Shuffling:");
        System.out.println(colors);

        // Shuffle the elements
        Collections.shuffle(colors);

        // Display ArrayList after shuffling
        System.out.println("After Shuffling:");
        System.out.println(colors);
    }
}