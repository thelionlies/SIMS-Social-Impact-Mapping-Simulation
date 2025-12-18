import java.awt.*;
import java.util.ArrayList;

public class OldMale extends Skin {

    public OldMale(double x, double y) {
        super(x, y, Color.WHITE, "m3", 17); // placeholder text/size

        // Head
        parts.add(new Circle(0, 0, 30, Color.decode("#FEEAB7")));

        // Body (shirt uses agent color)
        Triangle body = new Triangle(0, 32, 30, 32, 15, 62, this.color);
        parts.add(body);
        shirtPart = body; // reference for color updates

        // Glasses
        parts.add(new Arc(6, 12, 8, 8, 0, 360, 2, Color.BLACK));
        parts.add(new Arc(17, 12, 8, 8, 0, 360, 2, Color.BLACK));
        parts.add(new Line(14, 16, 17, 16, 2, Color.BLACK));
        parts.add(new Line(2, 12, 6, 14, 2, Color.BLACK));
        parts.add(new Line(25, 14, 28, 13, 2, Color.BLACK));

        // Hair
        parts.add(new Semicircle(5, 0, 21, 10, Color.GRAY));
    }
}
