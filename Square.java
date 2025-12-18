import java.awt.*;
import java.awt.geom.*;

public class Square extends DrawingObject {

    private double x;
    private double y;
    private double size;
    private Color color;

    public Square(double x, double y, double size, Color color) {
        super(x, y, color); // optional, in case DrawingObject uses color
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        Rectangle2D.Double square = new Rectangle2D.Double(x, y, size, size);
        g2d.fill(square);
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
