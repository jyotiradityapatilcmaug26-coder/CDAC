import java.util.ArrayList;
import java.util.Collections;

public class ArrayList_Reverse {

    public static void main(String[] args) {

        // Create an ArrayList
        ArrayList<String> colors = new ArrayList<>();

        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");
        colors.add("Black");

        // Display ArrayList before reversing
        System.out.println("Before Reversing:");
        System.out.println(colors);

        // Reverse the elements
        Collections.reverse(colors);

        // Display ArrayList after reversing
        System.out.println("After Reversing:");
        System.out.println(colors);
    }
}