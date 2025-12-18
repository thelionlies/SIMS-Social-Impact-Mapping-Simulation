import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Agent extends DrawingObject {

    // -------------------- Enums --------------------
    public enum Sex { MALE, FEMALE }
    public enum Age { YOUNG, MIDDLE, OLD }

    // -------------------- State --------------------
    private Sex sex;
    private Age age;
    private final Random rand = new Random();
    private double dx;
    private double dy;
    private double baseSpeed = 5;
    private double width;
    private double height;
    private Skin skinObject;
    private SimulationPlatform platform;
    private boolean visible = false;

    // -------------------- Defaults --------------------
    public static final double DEFAULT_WIDTH = 30 * 0.7;
    public static final double DEFAULT_HEIGHT = 62 * 0.7;
    public static final Color RED = new Color(0xB63C27);
    public static final Color BLUE = new Color(0x2D61B4);
    public static final Color GRAY = Color.GRAY;

    // -------------------- Highlight for color change --------------------
    private boolean highlight = false;
    private long highlightEndTime = 0;
    private static final Color HIGHLIGHT_COLOR = Color.YELLOW;
    private static final long HIGHLIGHT_DURATION_MS = 250;

    // -------------------- Constructors --------------------
    // Constructor with sex, age, and initial color
    public Agent(double x, double y, Sex sex, Age age, Color color, SimulationPlatform platform) {
        super(x, y, color);
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.platform = platform;

        this.sex = sex;
        this.age = age;

        // Assign skin immediately based on sex and age
        assignSkin();

        // Set initial color and update shirt
        setColor(color);

        // Initialize random movement
        double angle = rand.nextDouble() * 2 * Math.PI;
        dx = Math.cos(angle);
        dy = Math.sin(angle);
    }

    // -------------------- Drawing --------------------
    @Override
    public void draw(Graphics2D g2d) {
        long currentTime = System.currentTimeMillis();
        if (highlight && currentTime > highlightEndTime) {
            highlight = false;
        }

        if (visible) {
            g2d.setColor(color);
            g2d.fillRect((int)x, (int)y, (int)width, (int)height);
        }

        if (skinObject != null) {
            skinObject.setPosition(x, y);
            skinObject.draw(g2d);
        }

        if (highlight) {
            g2d.setColor(HIGHLIGHT_COLOR);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect((int)x, (int)y, (int)width, (int)height);
        }
    }

    // -------------------- Color --------------------
    @Override
    public void setColor(Color newColor) {
        if (!newColor.equals(this.color)) {
            highlight = true;
            highlightEndTime = System.currentTimeMillis() + HIGHLIGHT_DURATION_MS;
        }
        this.color = newColor;

        // Update shirt color
        if (skinObject != null && skinObject.shirtPart != null) {
            skinObject.shirtPart.setColor(newColor);
        }
    }

    // -------------------- Skin --------------------
    public void assignSkin() {
        if (age == null || sex == null) return;

        if (age == Age.YOUNG) {
            if (sex == Sex.MALE) {
                skinObject = new YoungMale(x, y);
            } else {
                skinObject = new YoungFemale(x, y);
            }
        } else if (age == Age.MIDDLE) {
            if (sex == Sex.MALE) {
                skinObject = new MiddleagedMale(x, y);
            } else {
                skinObject = new MiddleagedFemale(x, y);
            }
        } else if (age == Age.OLD) {
            if (sex == Sex.MALE) {
                skinObject = new OldMale(x, y);
            } else {
                skinObject = new OldFemale(x, y);
            }
        }

        // Immediately update shirt color to match agent color
        if (skinObject != null && skinObject.shirtPart != null) {
            skinObject.shirtPart.setColor(color);
        }
    }

    public void setSex(String sexStr) {
        if (sexStr == null) return;

        if (sexStr.equalsIgnoreCase("male")) {
            sex = Sex.MALE;
        } else if (sexStr.equalsIgnoreCase("female")) {
            sex = Sex.FEMALE;
        }
        assignSkin();
    }

    public void setAge(String ageStr) {
        if (ageStr == null) return;

        if (ageStr.equalsIgnoreCase("young")) {
            age = Age.YOUNG;
        } else if (ageStr.equalsIgnoreCase("middle")) {
            age = Age.MIDDLE;
        } else if (ageStr.equalsIgnoreCase("old")) {
            age = Age.OLD;
        }
        assignSkin();
    }

    // -------------------- Opinion dynamics --------------------
    public void applyOpinionFormula(List<Agent> allAgents, double cAge, double cSex) {
        double radius = 100; // influence radius in pixels

        List<Agent> neighborsP = new ArrayList<>(); // "opposite" color neighbors
        List<Agent> neighborsS = new ArrayList<>(); // "same" color neighbors

        for (Agent other : allAgents) {
            if (other == this) continue;

            double dx = centerX() - other.centerX();
            double dy = centerY() - other.centerY();
            double dist2 = dx * dx + dy * dy;
            
            if (dist2 > radius * radius) continue;

            // Gray agent special case
            if (this.color.equals(GRAY)) {
                if (other.color.equals(RED)) neighborsP.add(other);
                else if (other.color.equals(BLUE)) neighborsS.add(other);
            } else { // Red or Blue agent
                if (other.color.equals(this.color)) neighborsS.add(other);
                else if (!other.color.equals(GRAY)) neighborsP.add(other); // ignore gray
            }
        }

        // Compute influence values
        double i_p = computeInfluence(neighborsP, cAge, cSex);
        double i_s = computeInfluence(neighborsS, cAge, cSex);

        // Decide color change
        if (this.color.equals(GRAY)) {
            // First time choosing a color based on influence
            if (i_p - i_s > 0) {
                setColor(RED);
            } else if (i_p - i_s < 0) {
                setColor(BLUE);
            }
            // if i_p - i_s == 0, keep as GRAY (no change)
        } else {
            // Non-gray agent: switch only if influence favors opposite color
        if ((this.color.equals(RED) && !neighborsP.isEmpty() && i_p - i_s < 0) || 
            (this.color.equals(BLUE) && !neighborsP.isEmpty() && i_p - i_s > 0)) {
            switchOpinion();
        }
            // Otherwise, keep current color
        }
    }

    private double computeInfluence(List<Agent> neighbors, double cAge, double cSex) {
        if (neighbors.isEmpty()) return 0;

        double sum = 0;
        for (Agent other : neighbors) {
            double dx = centerX() - other.centerX();
            double dy = centerY() - other.centerY();
            double dist2 = dx * dx + dy * dy;

            sum += (cAge * ageInfluence(other) + cSex * sexInfluence(other)) / dist2;
        }

        return Math.sqrt(neighbors.size()) * (sum / neighbors.size());
    }

    private double sexInfluence(Agent other) {
        if (sex == null || other.sex == null) return 0;

        if (sex == other.sex) {
            return 1.0;
        } else {
            return 0.5;
        }
    }

    private double ageInfluence(Agent other) {
        if (age == null || other.age == null) return 0;

        int diff = Math.abs(age.ordinal() - other.age.ordinal());

        if (diff == 0) return 1.0;
        if (diff == 1) return 0.5;
        return 0.0;
    }

    private void switchOpinion() {
        if (color.equals(RED)) setColor(BLUE);
        else if (color.equals(BLUE)) setColor(RED);
    }

    // -------------------- Movement --------------------
    public void move(List<Agent> allAgents, double speedMultiplier) {
        update(allAgents.toArray(new DrawingObject[0]), speedMultiplier);
    }

    public void update(DrawingObject[] allObjects, double speedMultiplier) {
        double speed = baseSpeed * speedMultiplier;
        x += dx * speed;
        y += dy * speed;

        if (platform != null) {
            bounceIfOutOfBounds();
            x = platform.clampX(x, width);
            y = platform.clampY(y, height);
        }

        handleCollisions(allObjects);
    }

    private void bounceIfOutOfBounds() {
        if (platform == null) return;

        double left = platform.getX();
        double top = platform.getY();
        double right = left + platform.getWidth();
        double bottom = top + platform.getHeight();

        if (x <= left) {
            x = left;
            setDirectionRandom(-Math.PI / 4, Math.PI / 4);
        } else if (x + width >= right) {
            x = right - width;
            setDirectionRandom(3 * Math.PI / 4, 5 * Math.PI / 4);
        }

        if (y <= top) {
            y = top;
            setDirectionRandom(Math.PI / 4, 3 * Math.PI / 4);
        } else if (y + height >= bottom) {
            y = bottom - height;
            setDirectionRandom(5 * Math.PI / 4, 7 * Math.PI / 4);
        }
    }

    private void handleCollisions(DrawingObject[] allObjects) {
        for (DrawingObject obj : allObjects) {
            if (obj == this) continue;
            if (!(obj instanceof Agent)) continue;

            Agent other = (Agent) obj;
            if (!isColliding(other)) continue;

            double overlapX = (width + other.width) / 2 - Math.abs(centerX() - other.centerX());
            double overlapY = (height + other.height) / 2 - Math.abs(centerY() - other.centerY());

            if (overlapX < overlapY) {
                if (centerX() < other.centerX()) {
                    x -= overlapX;
                } else {
                    x += overlapX;
                }
                dx = -dx;
            } else {
                if (centerY() < other.centerY()) {
                    y -= overlapY;
                } else {
                    y += overlapY;
                }
                dy = -dy;
            }
        }
    }

    private void setDirectionRandom(double minAngle, double maxAngle) {
        double angle = minAngle + rand.nextDouble() * (maxAngle - minAngle);
        dx = Math.cos(angle);
        dy = Math.sin(angle);
    }

    private boolean isColliding(Agent other) {
        return !(x + width <= other.x || x >= other.x + other.width ||
                y + height <= other.y || y >= other.y + other.height);
    }

    // -------------------- Getters --------------------
    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    private double centerX() {
        return x + width / 2.0;
    }

    private double centerY() {
        return y + height / 2.0;
    }

    // -------------------- Position --------------------
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
