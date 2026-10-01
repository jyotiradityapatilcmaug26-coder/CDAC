import java.util.function.Supplier;

public class Lambda_RandomOTP {

    public static void main(String[] args) {

        // Lambda expression to generate a 5-character OTP
        Supplier<String> generateOTP = () -> {

            // Generate first character as a vowel
            String vowels = "AEIOU";
            char firstChar =
                    vowels.charAt((int) (Math.random() * vowels.length()));

            // Generate 4 random digits
            StringBuilder otp = new StringBuilder();
            otp.append(firstChar);

            for (int i = 0; i < 4; i++) {
                int digit = (int) (Math.random() * 10);
                otp.append(digit);
            }

            return otp.toString();
        };

        System.out.println("Generated OTP: " + generateOTP.get());
    }
}