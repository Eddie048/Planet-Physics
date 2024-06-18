import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The control panel holds the controls and canvas of the program
 */
public class ControlPanel extends JPanel {
    private final PlanetCanvas canvas;
    private final JComboBox<String> choosePlanetType;
    private final JComboBox<String> moveView;
    private boolean isRunning = false;
    private Timer timer;

    public ControlPanel(PlanetCanvas canvas) {
        this.canvas = canvas;

        // Add button to toggle the motion of the planets
        JButton toggleButton = new JButton("Toggle Motion");
        toggleButton.addActionListener(new ToggleButtonListener());
        add(toggleButton);

        // Add button to create new planets of various types
        String[] planetTypeNames = {"Sun", "Planet", "Satellite Close", "Satellite Far", "Planet Far"};
        choosePlanetType = new JComboBox<>(planetTypeNames);
        choosePlanetType.addActionListener(new PlanetButtonListener());
        add(choosePlanetType);

        // Add button to change the view
        String[] moveType = {"Up", "Down", "Left", "Right", "Zoom Out", "Zoom in"};
        moveView = new JComboBox<>(moveType);
        moveView.addActionListener(new ViewButtonListener());
        add(moveView);
    }

    /**
     * Listener to listen to the toggle button
     */
    private class ToggleButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Toggle running status
            isRunning = !isRunning;

            if (isRunning) {
                // Planets are now running, start timer
                timer = new Timer();
                TimerTask task = new Updater(canvas);
                timer.schedule(task, 500, 10);

            } else {
                // Planets should stop, cancel timer
                timer.cancel();
            }
        }
    }

    /**
     * Listener to listen to create planet button
     */
    private class PlanetButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            canvas.addPlanet(choosePlanetType.getSelectedIndex());
        }
    }

    /**
     * Listener to listen to change view button
     */
    private class ViewButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            canvas.moveView(moveView.getSelectedIndex());
        }
    }

    /**
     * TimerTask that runs the update loop
     */
    private static class Updater extends TimerTask {
        private final PlanetCanvas canvas;
        public Updater(PlanetCanvas c){
            canvas = c;
        }

        public void run() {
            canvas.updatePlanets();
        }
    }
}