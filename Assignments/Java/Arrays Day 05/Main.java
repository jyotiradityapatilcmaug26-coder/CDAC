import java.util.Scanner;

class ComplexNumber {
    // Two data members
    private int number1;
    private int number2;

    // Getters and Setters
    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    // Method to multiply values and return the product
    public int computeComplexNumber() {
        return this.number1 * this.number2;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Array of ComplexNumber having 5 elements
        ComplexNumber[] complexArray = new ComplexNumber[5];

        // Loop to take user input and populate objects
        for (int i = 0; i < complexArray.length; i++) {
            complexArray[i] = new ComplexNumber();

            System.out.println("Enter values for ComplexNumber " + (i + 1) + ":");
            System.out.print("Enter number1: ");
            int num1 = scanner.nextInt();
            complexArray[i].setNumber1(num1);

            System.out.print("Enter number2: ");
            int num2 = scanner.nextInt();
            complexArray[i].setNumber2(num2);
        }

        // Call computeComplexNumber on each object and display the result
        System.out.println("\n--- Results ---");
        for (int i = 0; i < complexArray.length; i++) {
            int result = complexArray[i].computeComplexNumber();
            System.out.println("Object " + (i + 1) + " (number1: " + complexArray[i].getNumber1() 
                + ", number2: " + complexArray[i].getNumber2() + ") Result: " + result);
        }

        scanner.close();
    }
}