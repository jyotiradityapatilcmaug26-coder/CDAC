// Base class Shape
class Shape {
    public void printShape() {
        System.out.println("This is shape");
    }
}

// Subclass Rectangle inheriting from Shape (Hierarchical Inheritance)
class Rectangle extends Shape {
    public void printRectangle() {
        System.out.println("This is rectangular shape");
    }
}

// Subclass Circle inheriting from Shape
class Circle extends Shape {
    public void printCircle() {
        System.out.println("This is circular shape");
    }
}

// Subclass Square inheriting from Rectangle (Multilevel Inheritance)
class Square extends Rectangle {
    public void printSquare() {
        System.out.println("Square is a rectangle");
    }
}

public class Question4 {
    public static void main(String[] args) {
        // Create an object of Square class
        Square squareObj = new Square();

        System.out.println("--- Calling methods using Square object ---");

        // Calling method of Shape class by the object of Square class
        squareObj.printShape();

        // Calling method of Rectangle class by the object of Square class
        squareObj.printRectangle();

        // (Optional) Calling Square's own method
        squareObj.printSquare();
    }
}