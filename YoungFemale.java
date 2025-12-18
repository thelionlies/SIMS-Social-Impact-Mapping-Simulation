import java.awt.*;
import java.util.ArrayList;

public class YoungFemale extends Skin {

    public YoungFemale(double x, double y) {
        super(x, y, Color.WHITE, "f1", 17); // placeholder text/size

        // Head
        parts.add(new Circle(0, 0, 30, Color.decode("#FEEAB7")));

        // Hair (left and right)
        parts.add(new Rectangle(0, 10, 5, 20, Color.BLACK));
        parts.add(new Rectangle(25, 10, 5, 20, Color.BLACK));

        // Cap layers
        parts.add(new Semicircle(0, 0, 30, 20, Color.decode("#B63C27")));  
        parts.add(new Semicircle(5, 0, 20, 20, Color.decode("#ECC11C")));  
        parts.add(new Semicircle(10, 0, 10, 20, Color.decode("#2D61B4"))); 

        // Body (uses agent's color)
        Triangle body = new Triangle(15, 32, 0, 62, 30, 62, this.color);
        parts.add(body);

        // Mark the shirt part so only it changes color
        shirtPart = body;
    }
}
