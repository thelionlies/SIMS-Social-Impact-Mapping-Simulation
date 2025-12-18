import java.awt.*;
import java.util.ArrayList;

public class MiddleagedFemale extends Skin {

    public MiddleagedFemale(double x, double y) {
        super(x, y, Color.WHITE, "f2", 17); // placeholder text/size

        // Head
        parts.add(new Circle(0, 0, 30, Color.decode("#FEEAB7")));

        // Hair
        parts.add(new Semicircle(0, 0, 30, 20, Color.BLACK));
        parts.add(new Rectangle(0, 10, 5, 20, Color.BLACK));
        parts.add(new Rectangle(25, 10, 5, 20, Color.BLACK));

        // Body (shirt uses agent color)
        Triangle body = new Triangle(15, 32, 0, 62, 30, 62, this.color);
        parts.add(body);
        shirtPart = body; // only this part changes color

        // Necktie (fixed color)
        parts.add(new Rectangle(10, 32, 10, 3, Color.decode("#1D2440")));
        parts.add(new Rectangle(13, 32, 4, 18, Color.decode("#1D2440")));
        parts.add(new Triangle(13, 50, 15, 54, 17, 50, Color.decode("#1D2440")));
    }
}
