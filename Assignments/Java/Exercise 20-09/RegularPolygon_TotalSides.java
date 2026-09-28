interface RegularPolygon {

    int getNumSides();

    double getSideLength();
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

public class RegularPolygon_TotalSides {

    // Static method to calculate total number of sides
    public static int totalSides(RegularPolygon[] polygons) {

        int total = 0;

        for (RegularPolygon polygon : polygons) {
            total = total + polygon.getNumSides();
        }

        return total;
    }

    public static void main(String[] args) {

        RegularPolygon triangle = new EquilateralTriangle(5);
        RegularPolygon square = new Square(4);

        RegularPolygon[] polygons = {
            triangle,
            square
        };

        System.out.println("Total Sides: " + totalSides(polygons));
    }
}