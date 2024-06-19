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

        // Add 2 planets orbiting a third planet in a stable orbit
        canvas.addPlanet(new Planet(100, 1, 0, 0, 50));
        canvas.addPlanet(new Planet(10000, 0, 0, 0, 0));
        canvas.addPlanet(new Planet(100, -1, 0, 0, -50));
    }

    public static void main(String[] args) {
        // Initialize window with size and close functionality
        PlanetDraw window = new PlanetDraw();
        window.setBounds(0, 0, 800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}