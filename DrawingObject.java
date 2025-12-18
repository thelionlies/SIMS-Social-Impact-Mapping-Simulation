import java.awt.*;

public abstract class DrawingObject {
    protected double x, y;      // Position (top-left corner)
    protected Color color;

    public DrawingObject(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    // Getters and Setters
    public double getX() { return x; }
    public double getY() { return y; }
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    public double getWidth() { return 0; }
    public double getHeight() { return 0; }

    public void adjustX(double distance) { x += distance; }
    public void adjustY(double distance) { y += distance; }

    public abstract void draw(Graphics2D g2d);

}
