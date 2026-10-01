import java.util.function.BiFunction;

public class Lambda_StringConcatenate {

    public static void main(String[] args) {

        String str1 = "Hello ";
        String str2 = "Java";

        // Lambda expression to concatenate two strings
        BiFunction<String, String, String> concatenate =
                (a, b) -> a + b;

        String result = concatenate.apply(str1, str2);

        System.out.println("Concatenated String: " + result);
    }
}