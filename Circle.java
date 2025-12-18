import java.awt.*;
import java.awt.geom.*;

public class Circle extends DrawingObject {

    private double size;

    public Circle(double x, double y, double size, Color color) {
        super(x, y, color);
        this.size = size;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        // Use the DrawingObject's x and y
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, size, size);
        g2d.fill(circle);
    }

    @Override
    public void adjustX(double distance) {
        x += distance;
    }

    @Override
    public void adjustY(double distance) {
        y += distance;
    }
}
