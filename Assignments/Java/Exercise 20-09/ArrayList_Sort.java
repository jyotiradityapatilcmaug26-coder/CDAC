import java.util.ArrayList;
import java.util.Collections;

public class ArrayList_Sort {

    public static void main(String[] args) {

        ArrayList<String> colors = new ArrayList<>();

        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");
        colors.add("Black");

        System.out.println("Before Sorting:");
        System.out.println(colors);

        Collections.sort(colors);

        System.out.println("After Sorting:");
        System.out.println(colors);
    }
}