import java.awt.*;
import java.awt.geom.*;

public class Rectangle extends DrawingObject {

    private double width;
    private double height;

    public Rectangle(double x, double y, double width, double height, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x, y, width, height);
        g2d.fill(rectangle);
    }

    @Override
    public void adjustX(double distance) {
        x += distance;
    }

    @Override
    public void adjustY(double distance) {
        y += distance;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
}
