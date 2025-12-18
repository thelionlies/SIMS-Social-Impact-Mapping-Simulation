import java.awt.*;
import java.util.ArrayList;

public class Background extends DrawingObject {

    private double width;
    private double height;
    private ArrayList<DrawingObject> parts;

    public Background(double width, double height) {
        super(0, 0, null);
        this.width = width;
        this.height = height;
        this.parts = new ArrayList<>();

        // Ground
        parts.add(new Rectangle(0, 0, width, height, Color.decode("#E5D5BC")));

        // Grid lines
        Color gridColor = new Color(134, 118, 93, 50);
        for (double x = 0; x <= width; x += 40) {
            parts.add(new Line(x, 0, x, height, 2, gridColor));
        }
        for (double y = 0; y <= height; y += 40) {
            parts.add(new Line(0, y, width, y, 2, gridColor));
        }

        // Road
        parts.add(new Rectangle(0, height - 35, width, 35, Color.decode("#2A2A2A")));
        parts.add(new Line(0, height - 25, width, height - 25, 3, Color.YELLOW));
        parts.add(new Line(0, height - 15, width, height - 15, 3, Color.YELLOW));

        // Grass near road
        parts.add(new Rectangle(60, height - 100, 180, 50, Color.decode("#595E08")));
        parts.add(new Rectangle(width - 240, height - 100, 180, 50, Color.decode("#595E08")));

        // Small grass patches
        Color smallGrass = Color.decode("#475C24");
        parts.add(new Rectangle(255, 480, 20, 10, smallGrass));
        parts.add(new Rectangle(290, 480, 20, 10, smallGrass));
        parts.add(new Rectangle(325, 480, 20, 10, smallGrass));
        parts.add(new Rectangle(255, 450, 20, 10, smallGrass));
        parts.add(new Rectangle(290, 450, 20, 10, smallGrass));
        parts.add(new Rectangle(325, 450, 20, 10, smallGrass));

        // Brick paths
        parts.add(new Rectangle(60, 0, 30, 320, Color.decode("#D8926B")));
        parts.add(new Rectangle(510, 0, 30, 320, Color.decode("#D8926B")));

        // Large grass borders (left)
        Path leftGrass = new Path(0, 0, Color.decode("#4C5022"), Color.decode("#A3BB59"), true, 4.0f);
        leftGrass.moveTo(90, 0);
        leftGrass.lineTo(90, 320);
        leftGrass.lineTo(120, 350);
        leftGrass.lineTo(200, 350);
        leftGrass.lineTo(200, 290);
        leftGrass.lineTo(260, 290);
        leftGrass.lineTo(260, 160);
        leftGrass.lineTo(230, 130);
        leftGrass.lineTo(230, 100);
        leftGrass.lineTo(200, 100);
        leftGrass.lineTo(200, 60);
        leftGrass.lineTo(230, 60);
        leftGrass.lineTo(230, 30);
        leftGrass.lineTo(260, 10);
        leftGrass.lineTo(260, 0);
        leftGrass.closePath();
        parts.add(leftGrass);

        // Large grass borders (right)
        Path rightGrass = new Path(0, 0, Color.decode("#4C5022"), Color.decode("#A3BB59"), true, 4.0f);
        rightGrass.moveTo(510, 0);
        rightGrass.lineTo(510, 320);
        rightGrass.lineTo(480, 350);
        rightGrass.lineTo(400, 350);
        rightGrass.lineTo(400, 290);
        rightGrass.lineTo(340, 290);
        rightGrass.lineTo(340, 160);
        rightGrass.lineTo(370, 130);
        rightGrass.lineTo(370, 100);
        rightGrass.lineTo(400, 100);
        rightGrass.lineTo(400, 60);
        rightGrass.lineTo(370, 60);
        rightGrass.lineTo(370, 30);
        rightGrass.lineTo(340, 10);
        rightGrass.lineTo(340, 0);
        rightGrass.closePath();
        parts.add(rightGrass);

        // Flagpole
        parts.add(new Square(250, 320, 100, Color.decode("#574A1B")));
        parts.add(new Square(260, 330, 80, Color.decode("#F9F5E9")));
        parts.add(new Square(280, 350, 40, Color.decode("#444039")));

        Path flagBlue = new Path(0, 0, Color.BLACK, Color.decode("#1E419B"), true, 0.5f);
        flagBlue.moveTo(302, 285);
        flagBlue.curveTo(320, 280, 340, 290, 352, 280);
        flagBlue.lineTo(352, 290);
        flagBlue.lineTo(302, 295);
        flagBlue.closePath();
        parts.add(flagBlue);

        Path flagRed = new Path(0, 0, Color.BLACK, Color.decode("#E03C44"), true, 0.5f);
        flagRed.moveTo(300, 305);
        flagRed.curveTo(315, 296, 328, 310, 350, 299);
        flagRed.lineTo(352, 290);
        flagRed.lineTo(302, 295);
        flagRed.closePath();
        parts.add(flagRed);

        parts.add(new Triangle(302, 285, 321, 293, 300, 305, Color.WHITE));
        parts.add(new Circle(307, 289, 3, Color.decode("#FBDC57")));
        parts.add(new Circle(307, 296, 3, Color.decode("#FBDC57")));
        parts.add(new Circle(315, 292, 3, Color.decode("#FBDC57")));
        parts.add(new Circle(309, 291, 5, Color.decode("#FBDC57")));

        parts.add(new Line(300, 370, 303, 285, 7, Color.decode("#A49C8E")));

        // Monument
        Path monumentBase = new Path(0, 0, Color.decode("#B28E6A"), Color.decode("#C6AD85"), true, 3.0f);
        monumentBase.moveTo(260, 100);
        monumentBase.lineTo(260, 60);
        monumentBase.lineTo(280, 40);
        monumentBase.lineTo(320, 40);
        monumentBase.lineTo(340, 60);
        monumentBase.lineTo(340, 100);
        monumentBase.lineTo(320, 120);
        monumentBase.lineTo(280, 120);
        monumentBase.closePath();
        parts.add(monumentBase);

        parts.add(new Rectangle(210, 70, 10, 20, Color.WHITE));
        parts.add(new Rectangle(380, 70, 10, 20, Color.WHITE));

        Path monumentProper = new Path(0, 0, Color.decode("#AD8965"), Color.decode("#F4E8CD"), true, 3.0f);
        monumentProper.moveTo(280, 70);
        monumentProper.lineTo(280, 90);
        monumentProper.lineTo(290, 100);
        monumentProper.lineTo(310, 100);
        monumentProper.lineTo(320, 90);
        monumentProper.lineTo(320, 70);
        monumentProper.lineTo(310, 60);
        monumentProper.lineTo(290, 60);
        monumentProper.closePath();
        parts.add(monumentProper);

        Path rizal = new Path(0, 0, Color.GRAY, Color.decode("#6F684C"), true, 1.0f);
        rizal.moveTo(290, 85);
        rizal.lineTo(295, 40);
        rizal.lineTo(305, 40);
        rizal.lineTo(310, 85);
        rizal.closePath();
        parts.add(rizal);
        parts.add(new Triangle(295, 40, 300, 30, 305, 40, Color.decode("#6F684C")));
    }

    @Override
    public void draw(Graphics2D g2d) {
        for (DrawingObject part : parts) {
            part.draw(g2d);
        }
    }
}
