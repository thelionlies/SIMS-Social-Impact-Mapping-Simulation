import java.awt.*;
import java.util.ArrayList;

public abstract class Skin extends DrawingObject {

    protected String text;
    protected int size;
    protected ArrayList<DrawingObject> parts;
    protected DrawingObject shirtPart;
    protected double scale = 0.7; // default 1.0, can be set per skin

    public Skin(double x, double y, Color color, String text, int size) {
        super(x, y, color);
        this.text = text;
        this.size = size;
        this.parts = new ArrayList<>();
    }

    /** Set the scale factor for the skin */
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.translate(x, y);       // move origin to top-left
        g2d.scale(scale, scale);   // apply scale
        for (DrawingObject part : parts) {
            part.draw(g2d);        // draw each part relative to 0,0
        }
        g2d.scale(1/scale, 1/scale); // reset scale
        g2d.translate(-x, -y);       // reset translation
    }

    /** Move the skin by updating top-left anchor; do NOT move parts individually */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Only update shirt color */
    public void setColor(Color newColor) {
        this.color = newColor;
        if (shirtPart != null) {
            shirtPart.setColor(newColor);
        }
    }
}
