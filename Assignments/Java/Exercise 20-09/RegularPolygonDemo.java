interface RegularPolygon {

    int getNumSides();

    double getSideLength();

    default double getPerimeter() {
        return getNumSides() * getSideLength();
    }

    default double getInteriorAngle() {
        return (getNumSides() - 2) * Math.PI / getNumSides();
    }
}

class EquilateralTriangle implements RegularPolygon {

    private double sideLength;

    public EquilateralTriangle(double sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public int getNumSides() {
        return 3;
    }

    @Override
    public double getSideLength() {
        return sideLength;
    }
}

class Square implements RegularPolygon {

    private double sideLength;

    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public int getNumSides() {
        return 4;
    }

    @Override
    public double getSideLength() {
        return sideLength;
    }
}

public class RegularPolygonDemo {

    public static void main(String[] args) {

        RegularPolygon triangle = new EquilateralTriangle(5);
        RegularPolygon square = new Square(4);

        System.out.println("Triangle Sides: " + triangle.getNumSides());
        System.out.println("Triangle Side Length: " + triangle.getSideLength());
        System.out.println("Triangle Perimeter: " + triangle.getPerimeter());

        System.out.println();

        System.out.println("Square Sides: " + square.getNumSides());
        System.out.println("Square Side Length: " + square.getSideLength());
        System.out.println("Square Perimeter: " + square.getPerimeter());
    }
}