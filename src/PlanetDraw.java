import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class PlanetDraw {

    private static boolean isRunning;
    private static Timer timer;

    public static void main(String[] args) {

        // Initialize window
        JFrame window = new JFrame("Planet Physics");
        window.setBounds(0, 0, 800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create planet canvas to display planets
        PlanetCanvas canvas = new PlanetCanvas();
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
        setupSolarSystem(canvas);
        window.add(canvas, BorderLayout.CENTER);

        // Create controls object
        JPanel controls = getControlPanel(canvas);
        window.add(controls, BorderLayout.SOUTH);

        // Render window
        window.setVisible(true);

        // Start planet motion
        isRunning = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canvas.updatePlanets();
            }
        }, 0, 2);
    }

    public static JPanel getControlPanel(PlanetCanvas canvas) {

        // Initialize controlPanel
        JPanel controlPanel = new JPanel();

        // Add button to toggle the motion of the planets
        JButton toggleButton = new JButton("Toggle Motion");
        toggleButton.addActionListener(e -> {
            isRunning = !isRunning;

            if (isRunning) {
                // Planets are now running, start timer
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        canvas.updatePlanets();
                    }
                }, 0, 2);

            } else {
                // Planets should stop, cancel timer
                timer.cancel();
            }
        });
        controlPanel.add(toggleButton);
        toggleButton.setFocusable(false);

        // Add button to specify new planet mass
        Integer[] planetMassOptions = {1, 10, 100, 1000, 10000};
        JComboBox<Integer> choosePlanetMass = new JComboBox<>(planetMassOptions);
        controlPanel.add(choosePlanetMass);
        choosePlanetMass.setFocusable(false);

        // Add follow random planet button
        JButton followButton = new JButton("Follow Planet");
        followButton.addActionListener(e -> canvas.setFollowPlanet());
        controlPanel.add(followButton);
        followButton.setFocusable(false);

        // Add stop following planet button
        JButton stopFollowing = new JButton("Stop following");
        stopFollowing.addActionListener(e -> canvas.stopFollowing());
        controlPanel.add(stopFollowing);
        stopFollowing.setFocusable(false);

        // Add view mode toggle button
        JButton viewToggle = new JButton("Toggle View Mode");
        viewToggle.addActionListener(e -> canvas.toggleViewMode());
        controlPanel.add(viewToggle);
        viewToggle.setFocusable(false);

        return controlPanel;
    }

    public static void setupSolarSystem(PlanetCanvas canvas) {
        // Earth Sun Moon representation, ratio of revolution time, ratio of orbit distance, mass ratios are all correct
        canvas.addPlanet(new Planet(27068780, 0, 0, 0, 0, 18)); // The Sun
        canvas.addPlanet(new Planet(81, Math.sqrt(270.0878/3902), 0, 0, 3902, 0.16)); // Earth
        canvas.addPlanet(new Planet(1, Math.sqrt(270.0878/3902) + Math.sqrt(0.00081/10), 0, 0, 3912, 0.045)); // The Moon
        canvas.addPlanet(new Planet(4.5, Math.sqrt(270.0878/1506), 0, 0, 1506, 0.063)); // Mercury
        canvas.addPlanet(new Planet(66.3, Math.sqrt(270.0878/2810), 0, 0, 2810, 0.157)); // Venus
        canvas.addPlanet(new Planet(8.7, Math.sqrt(270.0878/5931), 0, 0, 5931, 0.088)); // Mars
        canvas.addPlanet(new Planet(25837, Math.sqrt(270.0878/20265), 0, 0, 20265, 1.82)); // Jupiter
        canvas.addPlanet(new Planet(7739, Math.sqrt(270.0878/37122), 0, 0, 37122, 1.51)); // Saturn
        canvas.addPlanet(new Planet(1179, Math.sqrt(270.0878/74687), 0, 0, 74687, 0.66)); // Uranus
        canvas.addPlanet(new Planet(1390, Math.sqrt(270.0878/116987), 0, 0, 116987, 0.64)); // Neptune
        canvas.addPlanet(new Planet(0.2, Math.sqrt(270.0878/153824), 0, 0, 153824, 0.031)); // Pluto
    }

    public static void setupBinarySystem(PlanetCanvas canvas) {
        // Binary star system with a planet orbiting each star and exoplanet and exoplanet moon
        canvas.addPlanet(new Planet(100000, Math.sqrt(1.0/800), 0, 0, 200));
        canvas.addPlanet(new Planet(100000, -Math.sqrt(1.0/800), 0, 0, -200));
        canvas.addPlanet(new Planet(1, Math.sqrt(1.0/25) + Math.sqrt(1.0/800), 0, 0, 225));
        canvas.addPlanet(new Planet(1, -Math.sqrt(1.0/25) - Math.sqrt(1.0/800), 0, 0, -225));
        canvas.addPlanet(new Planet(200, Math.sqrt(1.0/1000), 0, 0, 2000));
        canvas.addPlanet(new Planet(1, Math.sqrt(1.0/1000) + Math.sqrt(0.00066666), 0, 0, 2003));
    }

    public static void setupSimpleBinary(PlanetCanvas canvas) {
        // 2 Center suns and 1 planet
        canvas.addPlanet(new Planet(100000, Math.sqrt(1.0/100), 0, 0, 25));
        canvas.addPlanet(new Planet(100000, -Math.sqrt(1.0/100), 0, 0, -25));
        canvas.addPlanet(new Planet(1, Math.sqrt(2.0/300), 0, 0, 300));
    }

    public static void setupSimpleSystem(PlanetCanvas canvas) {
        // Sun, planet, and moon
        canvas.addPlanet(new Planet(1000000, 0, 0, 0, 0));
        canvas.addPlanet(new Planet(200, Math.sqrt(1.0/30), 0, 0, 300));
        canvas.addPlanet(new Planet(1, Math.sqrt(1.0/30) + Math.sqrt(0.000666), 0, 0, 303));
    }
}