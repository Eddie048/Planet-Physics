import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class PlanetDraw extends JFrame {
    public PlanetDraw() {
        // Initialize JFrame, title Planet Physics
        super("Planet Physics");

        PlanetCanvas canvas = new PlanetCanvas();
        ControlPanel controls = new ControlPanel(canvas);
        Container c = getContentPane();
        c.add(canvas, BorderLayout.CENTER);
        c.add(controls, BorderLayout.SOUTH);
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();

        // Add 2 planets orbiting a third planet in a stable orbit
        // canvas.addPlanet(new Planet(1, 0.1 * Math.sqrt(2), 0, 0, 50));
        canvas.addPlanet(new Planet(1000000, 0, 0, 0, 0));
        canvas.addPlanet(new Planet(200, Math.sqrt(1.0/30), 0, 0, 300));
        canvas.addPlanet(new Planet(1, Math.sqrt(1.0/30) + Math.sqrt(0.000666), 0, 0, 303));
    }

    public static void main(String[] args) {
        // Initialize window with size and close functionality
        PlanetDraw window = new PlanetDraw();
        window.setBounds(0, 0, 800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}