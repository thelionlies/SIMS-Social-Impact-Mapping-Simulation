import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SceneCanvas extends JPanel implements Runnable {

    private final List<Agent> agents = new ArrayList<>();
    private final SimulationPlatform platform;
    private final SceneFrame frame;
    private final Random rand = new Random();
    private boolean running = false;
    private double speedMultiplier = 1.0;

    private Thread simulationThread;
    private Background background;

    private static final long MOVE_DURATION_MS = 9500;
    private static final long PAUSE_DURATION_MS = 250;
    private static final long FRAME_DELAY_MS = 30;

    public SceneCanvas(SceneFrame frame, SimulationPlatform platform) {
        this.frame = frame;
        this.platform = platform;
        setBackground(Color.WHITE);
        background = new Background(600, 600);
    }

    // Initialize agents with random properties
    public void initAgents(int totalAgents) {
        agents.clear();

        double boundsX = platform.getX();
        double boundsY = platform.getY();
        double boundsWidth = platform.getWidth();
        double boundsHeight = platform.getHeight();

        for (int i = 0; i < totalAgents; i++) {
            double x = boundsX + rand.nextDouble() * (boundsWidth - Agent.DEFAULT_WIDTH);
            double y = boundsY + rand.nextDouble() * (boundsHeight - Agent.DEFAULT_HEIGHT);

            Color color;
            int c = rand.nextInt(3);
            if (c == 0) color = SceneFrame.RED;
            else if (c == 1) color = SceneFrame.BLUE;
            else color = SceneFrame.GRAY;

            Agent.Sex sex = (rand.nextDouble() < 0.5) ? Agent.Sex.MALE : Agent.Sex.FEMALE;

            double r = rand.nextDouble();
            Agent.Age age;
            if (r < 0.33) age = Agent.Age.YOUNG;
            else if (r < 0.66) age = Agent.Age.MIDDLE;
            else age = Agent.Age.OLD;

            Agent a = new Agent(x, y, sex, age, color, platform);
            agents.add(a);
        }

        updateAgentCounts();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (platform != null) platform.draw(g2d);
        if (background != null) background.draw(g2d);

        for (Agent a : agents) {
            a.draw(g2d);
        }
    }

    public void startSimulation() {
        if (running) return;
        running = true;
        simulationThread = new Thread(this);
        simulationThread.start();
    }

    public void stopSimulation() {
        running = false;
    }

    public void setSpeedMultiplier(double multiplier) {
        this.speedMultiplier = multiplier;
    }

    @Override
    public void run() {
        while (running) {
            long moveStart = System.currentTimeMillis();

            while (running && System.currentTimeMillis() - moveStart < MOVE_DURATION_MS / speedMultiplier) {
                moveAgents();
                repaint();
                sleep((long)(FRAME_DELAY_MS / speedMultiplier));
            }

            if (!running) break;

            sleep((long)(PAUSE_DURATION_MS / speedMultiplier));
            applyOpinionChange();
            sleep((long)(PAUSE_DURATION_MS / speedMultiplier));
        }
    }

    private void moveAgents() {
        DrawingObject[] objs = agents.toArray(new DrawingObject[0]);
        for (Agent a : agents) {
            a.update(objs, speedMultiplier);
        }
    }

    private void applyOpinionChange() {
        double cAge = frame.getInfluenceBalance();
        double cSex = 1.0 - cAge;

        for (Agent a : agents) {
            a.applyOpinionFormula(agents, cAge, cSex);
        }

        updateAgentCounts();
        repaint();
    }

    public void addObject(Agent a) {
        agents.add(a);
    }

    public void clearAgents() {
        agents.clear();
    }

    public List<Agent> getAgents() {
        return agents;
    }

    private void updateAgentCounts() {
        int red = 0, blue = 0, gray = 0;
        for (Agent agent : agents) {
            if (agent.getColor().equals(SceneFrame.RED)) red++;
            else if (agent.getColor().equals(SceneFrame.BLUE)) blue++;
            else gray++;
        }
        frame.updateComposition(red, blue, gray);
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    public SimulationPlatform getPlatform() {
        return platform;
    }

    // ----- NEW METHODS -----
    public boolean isRunning() {
        return running;
    }

    public boolean hasAgents() {
        return !agents.isEmpty();
    }
}
