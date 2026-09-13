class Triangle {
    double a, b, c;

    // Parameterized constructor taking three sides
    Triangle(double side1, double side2, double side3) {
        a = side1;
        b = side2;
        c = side3;
    }

    // Method to calculate and return perimeter
    double getPerimeter() {
        return a + b + c;
    }

    // Method to calculate and return area using Heron's Formula
    double getArea() {
        double s = getPerimeter() / 2.0; // Semi-perimeter
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}

public class Question2 {
    public static void main(String[] args) {
        // Create an object of Triangle with sides 3, 4, and 5
        Triangle triangle = new Triangle(3, 4, 5);

        // Calculate and print the results
        System.out.println("Triangle with sides: 3, 4, 5 units");
        System.out.println("Perimeter : " + triangle.getPerimeter() + " units");
        System.out.println("Area      : " + triangle.getArea() + " sq units");
    }
}