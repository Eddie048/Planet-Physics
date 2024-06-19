import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The control panel holds the controls and canvas of the program
 */
public class ControlPanel extends JPanel {
    private boolean isRunning = false;
    private Timer timer;


    public ControlPanel(PlanetCanvas canvas) {

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
        add(toggleButton);

        // Add button to create new planets
        Integer[] planetMassOptions = {1, 10, 100, 1000, 10000};
        JComboBox<Integer> choosePlanetMass = new JComboBox<>(planetMassOptions);
        add(choosePlanetMass);

        // Add button to change the view
        String[] moveType = {"Up", "Down", "Left", "Right", "Zoom Out", "Zoom in"};
        JComboBox<String> moveView = new JComboBox<>(moveType);
        moveView.addActionListener(e -> canvas.moveView(moveView.getSelectedIndex()));
        add(moveView);

        // Add listener to the canvas so planets can be created
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = canvas.getMousePosition().x;
                int y = canvas.getMousePosition().y;
                int mass = planetMassOptions[choosePlanetMass.getSelectedIndex()];

                canvas.addPlanet(x, y, mass);
            }
        });
    }
}