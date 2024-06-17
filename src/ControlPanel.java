import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.util.Timer;
import java.util.TimerTask;


public class ControlPanel extends JPanel {
    private final DrawingPanel canvas;
    private final JComboBox<String> choosePlanetType;
    private final JComboBox<String> moveView;
    private boolean isRunning = false;
    private Timer timer;

    public ControlPanel(DrawingPanel canvas) {
        this.canvas = canvas;

        JButton updateButton = new JButton("Toggle Motion");
        updateButton.addActionListener(new ColorButtonListener());

        String[] planetTypeNames = {"Sun", "Planet", "Satellite Close", "Satellite Far", "Planet Far"};
        choosePlanetType = new JComboBox<>(planetTypeNames);

        choosePlanetType.addActionListener(new PlanetButtonListener());

        String[] moveType = {"Up", "Down", "Left", "Right", "Zoom Out", "Zoom in"};
        moveView = new JComboBox<>(moveType);

        moveView.addActionListener(new viewButtonListener());

        add(updateButton);

        add(moveView);

        add(choosePlanetType);
    }

    private class ColorButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!isRunning) {

                isRunning = true;

                timer = new Timer();
                TimerTask task = new Updater(canvas);

                timer.schedule(task, 500, 10);

            } else {
                timer.cancel();
                isRunning = false;
            }
        }
    }

    private class PlanetButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            canvas.addPlanet(choosePlanetType.getSelectedIndex() + 1);
            canvas.requestFocus();
        }
    }

    private class viewButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            canvas.moveView(moveView.getSelectedIndex() + 1);
            canvas.requestFocus();
        }
    }
}

class Updater extends TimerTask {
    private final DrawingPanel canvas;
    public Updater(DrawingPanel c){
        canvas = c;

    }

    public void run() {
        canvas.updatePlanets();
        canvas.requestFocus();
    }
}