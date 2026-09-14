class Rectangle {
    // Two data members
    protected double length;
    protected double breadth;

    // Constructor to initialize length and breadth
    public Rectangle(double length, double breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    // Method to calculate and print Area
    public void printArea() {
        double area = length * breadth;
        System.out.println("Area: " + area);
    }

    // Method to calculate and print Perimeter
    public void printPerimeter() {
        double perimeter = 2 * (length + breadth);
        System.out.println("Perimeter: " + perimeter);
    }
}

// Class Square inheriting Rectangle
class Square extends Rectangle {
    // Constructor having a single parameter for its side
    // Since a square has length == breadth, we pass 'side' to super(side, side)
    public Square(double side) {
        super(side, side);
    }
}

public class Question3 {
    public static void main(String[] args) {
        // Create an object of Rectangle with length = 5 and breadth = 8
        System.out.println("=== Rectangle Details ===");
        Rectangle rect = new Rectangle(5.0, 8.0);
        rect.printArea();
        rect.printPerimeter();

        // Create an object of Square with side = 4
        System.out.println("\n=== Square Details ===");
        Square sq = new Square(4.0);
        sq.printArea();
        sq.printPerimeter();
    }
}