import java.util.function.Supplier;

public class Lambda_Random3Digit {

    public static void main(String[] args) {

        // Lambda expression to generate a 3-digit random number
        Supplier<Integer> randomNumber =
                () -> 100 + (int) (Math.random() * 900);

        System.out.println("Random 3 Digit Number: "
                + randomNumber.get());
    }
}