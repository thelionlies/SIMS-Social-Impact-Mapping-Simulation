import java.awt.*;
import java.util.ArrayList;

public class OldFemale extends Skin {

    public OldFemale(double x, double y) {
        super(x, y, Color.WHITE, "f3", 17); // placeholder text/size

        // Head
        parts.add(new Circle(0, 0, 30, Color.decode("#FEEAB7")));

        // Body (shirt uses agent color)
        Triangle body = new Triangle(15, 32, 0, 62, 30, 62, this.color);
        parts.add(body);
        shirtPart = body; // only this part changes color

        // Glasses
        parts.add(new Arc(6, 12, 8, 8, 0, 360, 2, Color.BLACK));
        parts.add(new Arc(17, 12, 8, 8, 0, 360, 2, Color.BLACK));
        parts.add(new Line(14, 16, 17, 16, 2, Color.BLACK));
        parts.add(new Line(2, 12, 6, 14, 2, Color.BLACK));
        parts.add(new Line(25, 14, 28, 13, 2, Color.BLACK));

        // Hair
        parts.add(new Semicircle(0, 0, 30, 20, Color.GRAY));
        parts.add(new Rectangle(0, 10, 5, 20, Color.GRAY));
        parts.add(new Rectangle(25, 10, 5, 20, Color.GRAY));
    }
}
