import java.awt.*;
import java.util.ArrayList;

public class YoungMale extends Skin {

    public YoungMale(double x, double y) {
        super(x, y, Color.WHITE, "m1", 17); // placeholder text/size

        // Head
        parts.add(new Circle(0, 0, 30, Color.decode("#FEEAB7")));

        // Cap layers
        parts.add(new Semicircle(0, 0, 30, 20, Color.decode("#B63C27")));  
        parts.add(new Semicircle(5, 0, 20, 20, Color.decode("#ECC11C")));  
        parts.add(new Semicircle(10, 0, 10, 20, Color.decode("#2D61B4"))); 

        // Body (uses skin color)
        Triangle body = new Triangle(0, 32, 30, 32, 15, 62, this.color);
        parts.add(body);
        shirtPart = body; // <-- mark the shirt

    }
}
