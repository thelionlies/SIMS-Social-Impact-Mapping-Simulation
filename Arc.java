import java.awt.*;
import java.awt.geom.*;

public class Arc extends DrawingObject {

    private double width;
    private double height;
    private double startAngle;
    private double arcAngle;
    private float thickness;

    public Arc(double x, double y, double width, double height, double startAngle, double arcAngle, float thickness, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
        this.thickness = thickness;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));
        Arc2D.Double arc = new Arc2D.Double(x, y, width, height, startAngle, arcAngle, Arc2D.OPEN);
        g2d.draw(arc);
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
    public double getWidth() { return width; }

    @Override
    public double getHeight() { return height; }
}
