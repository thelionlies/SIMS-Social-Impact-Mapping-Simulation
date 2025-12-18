import java.awt.*;
import java.awt.geom.*;

public class Path extends DrawingObject { 
    private Path2D.Double path;
    private Color strokeColor;
    private Color fillColor;
    private boolean filled;
    private float strokeWidth;

    public Path(double x, double y, Color strokeColor, Color fillColor, boolean filled, float strokeWidth) {
        super(x, y, null); // DrawingObject color is unused
        this.path = new Path2D.Double();
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.filled = filled;
        this.strokeWidth = strokeWidth;
    }

    public void moveTo(double x, double y) {
        path.moveTo(x, y);
    }

    public void lineTo(double x, double y) {
        path.lineTo(x, y);
    }

    public void curveTo(double x1, double y1, double x2, double y2, double x3, double y3) {
        path.curveTo(x1, y1, x2, y2, x3, y3);
    }

    public void quadTo(double x1, double y1, double x2, double y2) {
        path.quadTo(x1, y1, x2, y2);
    }

    public void closePath() {
        path.closePath();
    }

    @Override
    public void draw(Graphics2D g2d) {
        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(strokeWidth));

        if (filled && fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fill(path);
        }
        
        if (strokeColor != null) {
            g2d.setColor(strokeColor);
            g2d.draw(path);
        }

        g2d.setStroke(originalStroke);
    }

    @Override
    public void adjustX(double distance) {
        path.transform(AffineTransform.getTranslateInstance(distance, 0));
    }

    @Override
    public void adjustY(double distance) {
        path.transform(AffineTransform.getTranslateInstance(0, distance));
    }
}
