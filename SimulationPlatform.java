import java.awt.*;

public class SimulationPlatform {
    private double x;
    private double y;
    private double width;
    private double height;

    public SimulationPlatform() {
        this.x = 10;
        this.y = 10;
        this.width = 580;
        this.height = 580;
    }

    // -----------------------------
    // Getters
    // -----------------------------
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    // -----------------------------
    // Utility methods
    // -----------------------------
    public double clampX(double value, double size) {
        return Math.max(x, Math.min(x + width - size, value));
    }

    public double clampY(double value, double size) {
        return Math.max(y, Math.min(y + height - size, value));
    }

    public boolean isOutOfBoundsX(double posX, double size) {
        return posX < x || posX + size > x + width;
    }

    public boolean isOutOfBoundsY(double posY, double size) {
        return posY < y || posY + size > y + height;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect((int)x, (int)y, (int)width, (int)height);
    }
}
