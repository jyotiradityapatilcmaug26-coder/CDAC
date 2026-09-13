class Rectangle {
    double length;
    double breadth;

    // Constructor to initialize length and breadth
    Rectangle(double length, double breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    // Method to calculate and return the area
    double area() {
        return length * breadth;
    }
}

public class Question3 {
    public static void main(String[] args) {
        // Creating two Rectangle objects with the given dimensions
        Rectangle rect1 = new Rectangle(4, 5);
        Rectangle rect2 = new Rectangle(5, 8);

        // Calculating and printing their areas
        System.out.println("Rectangle 1 (Sides: 4 x 5):");
        System.out.println("Area = " + rect1.area() + " sq units");
        System.out.println();

        System.out.println("Rectangle 2 (Sides: 5 x 8):");
        System.out.println("Area = " + rect2.area() + " sq units");
    }
}