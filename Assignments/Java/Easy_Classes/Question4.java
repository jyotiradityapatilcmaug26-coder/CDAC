import java.util.Scanner;

class Complex {
    double real;
    double imag;

    // Constructor to initialize real and imaginary parts
    Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    // Method to add two complex numbers
    Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imag + other.imag);
    }

    // Method to subtract two complex numbers
    Complex subtract(Complex other) {
        return new Complex(this.real - other.real, this.imag - other.imag);
    }

    // Method to multiply two complex numbers
    Complex multiply(Complex other) {
        double r = (this.real * other.real) - (this.imag * other.imag);
        double i = (this.real * other.imag) + (this.imag * other.real);
        return new Complex(r, i);
    }

    // Method to print the complex number nicely in (a + bi) or (a - bi) format
    void display() {
        if (imag >= 0) {
            System.out.println(real + " + " + imag + "i");
        } else {
            System.out.println(real + " - " + Math.abs(imag) + "i");
        }
    }
}

public class Question4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input for first complex number
        System.out.println("Enter the first complex number:");
        System.out.print("Enter real part: ");
        double r1 = scanner.nextDouble();
        System.out.print("Enter imaginary part: ");
        double i1 = scanner.nextDouble();
        Complex c1 = new Complex(r1, i1);

        // Input for second complex number
        System.out.println("\nEnter the second complex number:");
        System.out.print("Enter real part: ");
        double r2 = scanner.nextDouble();
        System.out.print("Enter imaginary part: ");
        double i2 = scanner.nextDouble();
        Complex c2 = new Complex(r2, i2);

        // Calling separate methods for each operation
        Complex sum = c1.add(c2);
        Complex diff = c1.subtract(c2);
        Complex prod = c1.multiply(c2);

        // Display results
        System.out.println("\n=== Results ===");
        System.out.print("First Complex Number  : ");
        c1.display();

        System.out.print("Second Complex Number : ");
        c2.display();

        System.out.print("Sum                   : ");
        sum.display();

        System.out.print("Difference (c1 - c2)  : ");
        diff.display();

        System.out.print("Product               : ");
        prod.display();

        scanner.close();
    }
}