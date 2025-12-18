import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SceneFrame extends JFrame {

    // Default settings
    private final int DEFAULT_RED = 20;
    private final int DEFAULT_BLUE = 20;
    private final int DEFAULT_GRAY = 10;
    private final double DEFAULT_SPEED = 1.0;
    private final int DEFAULT_MALE_PERCENT = 50;
    private final int DEFAULT_YOUNG_PERCENT = 33;
    private final int DEFAULT_MIDDLE_PERCENT = 33;

    // Custom agent colors
    public static final Color RED = new Color(0xB63C27);
    public static final Color BLUE = new Color(0x2D61B4);
    public static final Color GRAY = Color.GRAY;

    private SceneCanvas simulationCanvas;
    private JPanel controlPanel;
    private SimulationPlatform platform;

    private JLabel clockLabel;
    private JLabel multiplierLabel;
    private JLabel redCountLabel;
    private JLabel blueCountLabel;
    private JLabel grayCountLabel;

    private JTextField redField, blueField, grayField;
    private JSlider sexSlider;
    private JSlider youngSlider, middleSlider;
    private JSlider influenceSlider;

    private Timer clockTimer;
    private double[] speedLevels = {0.5, 0.75, 1, 2, 4};
    private int currentSpeedIndex = 2;
    private int elapsedSeconds = 0;

    private final Random rand = new Random();

    public SceneFrame() {
        super("SIMS: Social Impact Mapping Simulation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize platform
        platform = new SimulationPlatform();

        // Initialize canvas
        simulationCanvas = new SceneCanvas(this, platform);
        simulationCanvas.setPreferredSize(new Dimension(600, 600));
        simulationCanvas.setSpeedMultiplier(DEFAULT_SPEED);

        // Initialize control panel
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(200, 600));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setupTitle();
        setupDescription();
        setupOutput();
        setupInput();

        // Add canvas and controls to split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, simulationCanvas, controlPanel);
        splitPane.setDividerLocation(600);
        splitPane.setEnabled(false);
        splitPane.setDividerSize(0);
        add(splitPane, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // Initialize clock timer
        clockTimer = new Timer(1000, new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                elapsedSeconds++;
                clockLabel.setText("Time: " + elapsedSeconds + "s");
            }
        });
    }

    // Setup the title label
    private void setupTitle() {
        JLabel title = new JLabel("SIMS");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlPanel.add(title);
    }


    // Setup the description label
    private void setupDescription() {
        JLabel desc = new JLabel("<html><center>Social Impact Mapping Simulation<br>Based from the study of Nowak, Szamrej, & Latané (1990)</center></html>");
        desc.setFont(new Font("Arial", Font.PLAIN, 10));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlPanel.add(desc);


        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        controlPanel.add(Box.createVerticalStrut(0));
        controlPanel.add(separator);
        controlPanel.add(Box.createVerticalStrut(0));
    }

    // Setup simulation output panel
    private void setupOutput() {
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));


        // Panel to hold both time and speed sections
        JPanel timerSpeedPanel = new JPanel(new GridLayout(2, 1, 0, 5)); // 2 rows, 1 column, 5px vertical gap
        timerSpeedPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        // ----- TIME LABEL -----
        clockLabel = new JLabel("Time: 0s", SwingConstants.CENTER);
        timerSpeedPanel.add(clockLabel);


        // ----- SPEED CONTROLS -----
        JPanel speedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
        JButton leftBtn = new JButton("←");
        multiplierLabel = new JLabel("Speed: " + DEFAULT_SPEED + "x");
        JButton rightBtn = new JButton("→");


        Dimension buttonSize = new Dimension(20, 20);
        leftBtn.setPreferredSize(buttonSize);
        rightBtn.setPreferredSize(buttonSize);
        leftBtn.setMargin(new Insets(0, 0, 0, 0));
        rightBtn.setMargin(new Insets(0, 0, 0, 0));


        speedPanel.add(leftBtn);
        speedPanel.add(multiplierLabel);
        speedPanel.add(rightBtn);


        // Add speedPanel below time label
        timerSpeedPanel.add(speedPanel);


        // Add to output panel
        output.add(Box.createVerticalStrut(5));
        output.add(timerSpeedPanel);
        output.add(Box.createVerticalStrut(5));


        // Divider below if you still want it
        JSeparator speedSeparator = new JSeparator(JSeparator.HORIZONTAL);
        speedSeparator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        output.add(speedSeparator);


        // Create counts panel using a simple GridLayout (2 rows x 3 columns)
        JPanel countsPanel = new JPanel(new GridLayout(2, 3, 5, 0)); // small horizontal gap


        // Labels row
        JLabel redLabel = new JLabel("Red", SwingConstants.CENTER);
        JLabel blueLabel = new JLabel("Blue", SwingConstants.CENTER);
        JLabel grayLabel = new JLabel("Gray", SwingConstants.CENTER);


        // Count labels
        redCountLabel = new JLabel("0", SwingConstants.CENTER);
        blueCountLabel = new JLabel("0", SwingConstants.CENTER);
        grayCountLabel = new JLabel("0", SwingConstants.CENTER);


        // Add all components in order
        countsPanel.add(redLabel);
        countsPanel.add(blueLabel);
        countsPanel.add(grayLabel);
        countsPanel.add(redCountLabel);
        countsPanel.add(blueCountLabel);
        countsPanel.add(grayCountLabel);


        // Make it visually tighter
        countsPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));


        output.add(Box.createVerticalStrut(2));
        output.add(countsPanel);
        output.add(Box.createVerticalStrut(2));


        // Add separator after counts
        JSeparator bottomSeparator = new JSeparator(JSeparator.HORIZONTAL);
        bottomSeparator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        output.add(Box.createVerticalStrut(0));
        output.add(bottomSeparator);
       
        JPanel playPanel = new JPanel(new FlowLayout());
        JButton playBtn = new JButton("▶");
        JButton pauseBtn = new JButton("⏸");
        JButton resetBtn = new JButton("⟲");
        playPanel.add(playBtn);
        playPanel.add(pauseBtn);
        playPanel.add(resetBtn);
        output.add(playPanel);


        controlPanel.add(output);


        JSeparator instructionSeparator1 = new JSeparator(JSeparator.HORIZONTAL);
        instructionSeparator1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        output.add(instructionSeparator1);


        JLabel instructionLabel = new JLabel(
            "<html><div style='text-align:left;'>"
            + "Simulate how political influence flows around us!"
            + "<br> 1. Set demographic ratio below."
            + "<br> 2. Click ▶ to start."
            + "<br> 3. Adjust speed with ← and →."
            + "<br> 4. Look at the dynamics over time."
            + "</div></html>"
        );
        instructionLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // add some spacing
        controlPanel.add(instructionLabel);


        JSeparator instructionSeparator2 = new JSeparator(JSeparator.HORIZONTAL);
        instructionSeparator2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        controlPanel.add(instructionSeparator2);


        leftBtn.addActionListener(e -> {
            currentSpeedIndex = Math.max(0, currentSpeedIndex - 1);
            simulationCanvas.setSpeedMultiplier(speedLevels[currentSpeedIndex]);
            multiplierLabel.setText("Speed: " + speedLevels[currentSpeedIndex] + "x");
        });
        rightBtn.addActionListener(e -> {
            currentSpeedIndex = Math.min(speedLevels.length - 1, currentSpeedIndex + 1);
            simulationCanvas.setSpeedMultiplier(speedLevels[currentSpeedIndex]);
            multiplierLabel.setText("Speed: " + speedLevels[currentSpeedIndex] + "x");
        });
        playBtn.addActionListener(e -> startSimulation());
        pauseBtn.addActionListener(e -> pauseSimulation());
        resetBtn.addActionListener(e -> resetSimulation());
    }

    // Setup input panel
    private void setupInput() {
        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
        input.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
       
        JPanel agentPanel = new JPanel(new GridLayout(3, 2, 5, 5));


        JLabel redLabel = new JLabel("Red agents:");
        redLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        redField = new JTextField(String.valueOf(DEFAULT_RED));
        agentPanel.add(redLabel);
        agentPanel.add(redField);


        JLabel blueLabel = new JLabel("Blue agents:");
        blueLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        blueField = new JTextField(String.valueOf(DEFAULT_BLUE));
        agentPanel.add(blueLabel);
        agentPanel.add(blueField);


        JLabel grayLabel = new JLabel("Gray agents:");
        grayLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        grayField = new JTextField(String.valueOf(DEFAULT_GRAY));
        agentPanel.add(grayLabel);
        agentPanel.add(grayField);


        agentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        agentPanel.setMaximumSize(new Dimension(200, 80)); // prevent stretching too wide
        input.add(agentPanel);
        input.add(Box.createVerticalStrut(10));


        JPanel sexPanel = new JPanel();
        sexPanel.setLayout(new BoxLayout(sexPanel, BoxLayout.Y_AXIS));
        sexPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Create the slider
        JLabel sexLabel = new JLabel("Sex Distribution");
        sexPanel.add(sexLabel);
        sexSlider = new JSlider(0, 100, DEFAULT_MALE_PERCENT);
        sexSlider.setPaintTicks(false);
        sexSlider.setPaintLabels(false);
        sexSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        sexPanel.add(sexSlider);


        // Add "Male" and "Female" labels under the ends of the slider
        JPanel sexLabels = new JPanel(new BorderLayout());
        JLabel maleLabel = new JLabel("Male");
        JLabel femaleLabel = new JLabel("Female");
        maleLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        femaleLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        sexLabels.add(maleLabel, BorderLayout.WEST);
        sexLabels.add(femaleLabel, BorderLayout.EAST);


        sexPanel.add(sexLabels);


        // Add to input
        input.add(sexPanel);
        input.add(Box.createVerticalStrut(3));


        // =======================
        // AGE DISTRIBUTION PANEL
        // =======================
        JPanel agePanel = new JPanel();
        agePanel.setLayout(new BoxLayout(agePanel, BoxLayout.Y_AXIS));
        agePanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Title label
        JLabel ageTitle = new JLabel("Age Distribution");
        ageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        agePanel.add(ageTitle);
        agePanel.add(Box.createVerticalStrut(5));


        // ---------- First slider (Young ↔ Middle-age) ----------
        JPanel youngPanel = new JPanel();
        youngPanel.setLayout(new BoxLayout(youngPanel, BoxLayout.Y_AXIS));
        youngPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        youngSlider = new JSlider(0, 100, DEFAULT_YOUNG_PERCENT);
        youngSlider.setPaintTicks(false);
        youngSlider.setPaintLabels(false);
        youngSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        youngPanel.add(youngSlider);


        JPanel youngLabels = new JPanel(new BorderLayout());
        JLabel youngLabel = new JLabel("Young");
        JLabel middleLabel1 = new JLabel("Middle-age");
        youngLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        middleLabel1.setFont(new Font("SansSerif", Font.PLAIN, 10));
        youngLabels.add(youngLabel, BorderLayout.WEST);
        youngLabels.add(middleLabel1, BorderLayout.EAST);
        youngPanel.add(youngLabels);


        agePanel.add(youngPanel);
        agePanel.add(Box.createVerticalStrut(3));


        // ---------- Second slider (Middle-age ↔ Old) ----------
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        middleSlider = new JSlider(0, 100, DEFAULT_MIDDLE_PERCENT);
        middleSlider.setPaintTicks(false);
        middleSlider.setPaintLabels(false);
        middleSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        middlePanel.add(middleSlider);


        JPanel middleLabels = new JPanel(new BorderLayout());
        JLabel middleLabel2 = new JLabel("Middle-age");
        JLabel oldLabel = new JLabel("Old");
        middleLabel2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        oldLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        middleLabels.add(middleLabel2, BorderLayout.WEST);
        middleLabels.add(oldLabel, BorderLayout.EAST);
        middlePanel.add(middleLabels);


        agePanel.add(middlePanel);
        agePanel.add(Box.createVerticalStrut(3));


        // ---------- Percentage label ----------
        JLabel ageLabel = new JLabel("Young/Middle/Old: 33/33/34");
        ageLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        ageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, ageLabel.getPreferredSize().height));
        ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        agePanel.add(ageLabel);


        // ---------- Add the whole block ----------
        input.add(agePanel);
        input.add(Box.createVerticalStrut(8));


        ChangeListener ageListener = e -> {
            int young = youngSlider.getValue();
            int middle = middleSlider.getValue();
            if (middle > 100 - young) middle = 100 - young;
            middleSlider.setValue(middle);
            int old = 100 - young - middle;
            ageLabel.setText("Young/Middle/Old: " + young + "/" + middle + "/" + old);
        };
        youngSlider.addChangeListener(ageListener);
        middleSlider.addChangeListener(ageListener);


        // =======================
        // INFLUENCE DISTRIBUTION PANEL
        // =======================
        JPanel influenceContainer = new JPanel();
        influenceContainer.setLayout(new BoxLayout(influenceContainer, BoxLayout.Y_AXIS));
        influenceContainer.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Title label
        JLabel influenceTitle = new JLabel("Influence Scale");
        influenceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        influenceContainer.add(influenceTitle);
        influenceContainer.add(Box.createVerticalStrut(5));


        // Slider and labels in one line
        JPanel influencePanel = new JPanel(new BorderLayout());
        influenceSlider = new JSlider(0, 100, 50);
        influenceSlider.setPaintTicks(false);
        influenceSlider.setPaintLabels(false);
        influenceSlider.setMajorTickSpacing(20);
        influenceSlider.setMinorTickSpacing(0);


        JLabel leftLabel = new JLabel("Age");
        leftLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        JLabel rightLabel = new JLabel("Sex");
        rightLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));


        influencePanel.add(influenceSlider, BorderLayout.CENTER);
        influencePanel.add(leftLabel, BorderLayout.WEST);
        influencePanel.add(rightLabel, BorderLayout.EAST);


        // Add influence section to container
        influenceContainer.add(influencePanel);


        // Add padding below to separate it from other inputs
        influenceContainer.add(Box.createVerticalStrut(8));


        // Add to main input panel
        input.add(influenceContainer);




        // // Influence slider setup
        // JSlider influenceSlider = new JSlider(0, 100, 50);
        // influenceSlider.setPaintTicks(false);
        // influenceSlider.setPaintLabels(false); // hide numeric labels
        // influenceSlider.setMajorTickSpacing(20);
        // influenceSlider.setMinorTickSpacing(0);


        // // Create a small panel to hold the slider and labels neatly
        // JPanel influencePanel = new JPanel();
        // influencePanel.setLayout(new BorderLayout());


        // // Labels for the ends
        // JLabel leftLabel = new JLabel("Age");
        // leftLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        // JLabel rightLabel = new JLabel("Sex");
        // rightLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));


        // // Add components
        // influencePanel.add(influenceSlider, BorderLayout.CENTER);
        // influencePanel.add(leftLabel, BorderLayout.WEST);
        // influencePanel.add(rightLabel, BorderLayout.EAST);


        // // Add to your main input panel
        // input.add(influencePanel);


        controlPanel.add(input);
    }

    // Start or resume simulation
    private void startSimulation() {
        // Try parsing inputs first
        int redCount = 0;
        int blueCount = 0;
        int grayCount = 0;

        try {
            redCount = Integer.parseInt(redField.getText());
            blueCount = Integer.parseInt(blueField.getText());
            grayCount = Integer.parseInt(grayField.getText());
        } catch (NumberFormatException e) {
            clockLabel.setText("Invalid input");
            return;
        }

        int totalAgents = redCount + blueCount + grayCount;

        // Check constraints
        if (totalAgents > 60) {
            clockLabel.setText("Max agent is 60");
            return;
        }

        if (redCount == 0 && blueCount == 0) {
            clockLabel.setText("No Red/Blue Agent");
            return;
        }

        // --- Valid input ---
        clockLabel.setText("Time: " + elapsedSeconds + "s");

        if (!simulationCanvas.hasAgents()) {
            // New simulation: clear and add agents
            simulationCanvas.clearAgents();
            addAgentsToCanvas();
        }

        // Start or resume simulation
        if (!simulationCanvas.isRunning()) {
            simulationCanvas.startSimulation();
        }
        clockTimer.start();
    }


    // Pause simulation
    private void pauseSimulation() {
        simulationCanvas.stopSimulation();
        clockTimer.stop();
    }

    // Reset simulation
    private void resetSimulation() {
        pauseSimulation();

        redField.setText(String.valueOf(DEFAULT_RED));
        blueField.setText(String.valueOf(DEFAULT_BLUE));
        grayField.setText(String.valueOf(DEFAULT_GRAY));

        sexSlider.setValue(DEFAULT_MALE_PERCENT);
        youngSlider.setValue(DEFAULT_YOUNG_PERCENT);
        middleSlider.setValue(DEFAULT_MIDDLE_PERCENT);
        influenceSlider.setValue(50);

        currentSpeedIndex = 2;
        simulationCanvas.setSpeedMultiplier(speedLevels[currentSpeedIndex]);
        multiplierLabel.setText("Speed: " + speedLevels[currentSpeedIndex] + "x");

        simulationCanvas.clearAgents();
        simulationCanvas.repaint();

        elapsedSeconds = 0;
        clockLabel.setText("Time: 0s");

        // Reset count labels
        updateComposition(DEFAULT_RED, DEFAULT_BLUE, DEFAULT_GRAY);
    }

    // Add agents to the canvas (no changes needed inside, but remove previous error print)
    private void addAgentsToCanvas() {
        int redCount = Integer.parseInt(redField.getText());
        int blueCount = Integer.parseInt(blueField.getText());
        int grayCount = Integer.parseInt(grayField.getText());

        int malePercent = sexSlider.getValue();
        int youngPercent = youngSlider.getValue();
        int middlePercent = middleSlider.getValue();

        int totalAgents = redCount + blueCount + grayCount;

        // REMOVE the old console error check:
        // if (totalAgents > 80) { System.err.println(...); return; }

        List<Agent> allAgents = new ArrayList<Agent>();

        // Create agents with default Sex and Age
        for (int i = 0; i < redCount; i++) allAgents.add(new Agent(0, 0, Agent.Sex.MALE, Agent.Age.YOUNG, RED, platform));
        for (int i = 0; i < blueCount; i++) allAgents.add(new Agent(0, 0, Agent.Sex.MALE, Agent.Age.YOUNG, BLUE, platform));
        for (int i = 0; i < grayCount; i++) allAgents.add(new Agent(0, 0, Agent.Sex.MALE, Agent.Age.YOUNG, GRAY, platform));

        Collections.shuffle(allAgents);

        // Assign ages
        int youngCount = (int)Math.round(totalAgents * youngPercent / 100.0);
        int middleCount = (int)Math.round(totalAgents * middlePercent / 100.0);
        int oldCount = totalAgents - youngCount - middleCount;

        for (int i = 0; i < youngCount; i++) allAgents.get(i).setAge("young");
        for (int i = youngCount; i < youngCount + middleCount; i++) allAgents.get(i).setAge("middle");
        for (int i = youngCount + middleCount; i < totalAgents; i++) allAgents.get(i).setAge("old");

        // Assign sexes
        int maleCount = (int)Math.round(totalAgents * malePercent / 100.0);
        for (int i = 0; i < totalAgents; i++) {
            if (i < maleCount) allAgents.get(i).setSex("male");
            else allAgents.get(i).setSex("female");
        }

        // Randomly place agents
        for (Agent a : allAgents) {
            double x = platform.getX() + rand.nextDouble() * (platform.getWidth() - a.getWidth());
            double y = platform.getY() + rand.nextDouble() * (platform.getHeight() - a.getHeight());
            a.setPosition(x, y);
            simulationCanvas.addObject(a);
        }

        // Update counts in output panel
        updateComposition(redCount, blueCount, grayCount);
    }

    // Update agent counts in output panel
    public void updateComposition(int red, int blue, int gray) {
        redCountLabel.setText(String.valueOf(red));
        blueCountLabel.setText(String.valueOf(blue));
        grayCountLabel.setText(String.valueOf(gray));
    }

    // Influence balance from slider
    public double getInfluenceBalance() {
        return influenceSlider.getValue() / 100.0;
    }

    // Main entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SceneFrame();
            }
        });
    }
}
