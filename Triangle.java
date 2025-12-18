import java.awt.*;       // For Color, Polygon
import java.awt.Graphics2D; // For drawing

public class Triangle extends DrawingObject {

    private double x1, y1, x2, y2, x3, y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
        super(0, 0, color);
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
        this.x3 = x3; this.y3 = y3;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        Polygon triangle = new Polygon();
        triangle.addPoint((int)x1, (int)y1);
        triangle.addPoint((int)x2, (int)y2);
        triangle.addPoint((int)x3, (int)y3);
        g2d.fill(triangle);
    }

    @Override
    public void adjustX(double distance) {
        x1 += distance; x2 += distance; x3 += distance;
    }

    @Override
    public void adjustY(double distance) {
        y1 += distance; y2 += distance; y3 += distance;
    }
}
