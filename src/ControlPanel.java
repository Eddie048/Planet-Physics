import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The control panel holds the controls and canvas of the program
 */
public class ControlPanel extends JPanel {
    private final PlanetCanvas canvas;
    private final JComboBox<Integer> choosePlanetMass;
    private final JComboBox<String> moveView;
    private boolean isRunning = false;
    private Timer timer;

    private final Integer[] planetMassOptions = {1, 10, 100, 1000, 10000};

    public ControlPanel(PlanetCanvas canvas) {
        this.canvas = canvas;

        // Add button to toggle the motion of the planets
        JButton toggleButton = new JButton("Toggle Motion");
        toggleButton.addActionListener(new ToggleButtonListener());
        add(toggleButton);

        // Add button to create new planets
        choosePlanetMass = new JComboBox<>(planetMassOptions);
        add(choosePlanetMass);

        // Add button to change the view
        String[] moveType = {"Up", "Down", "Left", "Right", "Zoom Out", "Zoom in"};
        moveView = new JComboBox<>(moveType);
        moveView.addActionListener(new ViewButtonListener());
        add(moveView);

        // Add listener to the canvas so planets can be created
        canvas.addMouseListener(new CanvasClickListener());

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
                timer.schedule(task, 0, 10);

            } else {
                // Planets should stop, cancel timer
                timer.cancel();
            }
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

    private class CanvasClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = canvas.getMousePosition().x;
            int y = canvas.getMousePosition().y;
            int mass = planetMassOptions[choosePlanetMass.getSelectedIndex()];

            canvas.addPlanet(x, y, mass);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
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