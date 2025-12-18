import java.awt.*;
import java.awt.geom.*;

public class Line extends DrawingObject {

    private double x1, y1;
    private double x2, y2;
    private float thickness;

    public Line(double x1, double y1, double x2, double y2, float thickness, Color color) {
        super(0, 0, color); // x and y of DrawingObject are not used here
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.thickness = thickness;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    @Override
    public void adjustX(double distance) {
        x1 += distance;
        x2 += distance;
    }

    @Override
    public void adjustY(double distance) {
        y1 += distance;
        y2 += distance;
    }

    @Override
    public double getWidth() {
        return Math.abs(x2 - x1);
    }

    @Override
    public double getHeight() {
        return Math.abs(y2 - y1);
    }
}
