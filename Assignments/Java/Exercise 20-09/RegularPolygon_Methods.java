interface RegularPolygon {

    int getNumSides();

    double getSideLength();

    // Default method to calculate perimeter
    default double getPerimeter() {
        return getNumSides() * getSideLength();
    }

    // Default method to calculate interior angle
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

public class RegularPolygon_Methods {

    public static void main(String[] args) {

        RegularPolygon triangle = new EquilateralTriangle(5);
        RegularPolygon square = new Square(4);

        System.out.println("Triangle Perimeter: "
                + triangle.getPerimeter());

        System.out.println("Triangle Interior Angle: "
                + triangle.getInteriorAngle());

        System.out.println();

        System.out.println("Square Perimeter: "
                + square.getPerimeter());

        System.out.println("Square Interior Angle: "
                + square.getInteriorAngle());
    }
}