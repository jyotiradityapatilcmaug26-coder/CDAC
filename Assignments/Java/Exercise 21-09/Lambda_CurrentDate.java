import java.time.LocalDate;
import java.util.function.Supplier;

public class Lambda_CurrentDate {

    public static void main(String[] args) {

        // Lambda expression to get the current date
        Supplier<LocalDate> currentDate =
                () -> LocalDate.now();

        System.out.println("Current Date: " + currentDate.get());
    }
}