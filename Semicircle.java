import java.awt.*;
import java.awt.geom.*;

public class Semicircle extends DrawingObject {

    private double width, height;

    public Semicircle(double x, double y, double width, double height, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        Arc2D.Double semicircle = new Arc2D.Double(x, y, width, height, 0, 180, Arc2D.PIE);
        g2d.fill(semicircle);
    }
}
