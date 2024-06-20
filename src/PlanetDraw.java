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

        // Sun, planet, and moon
//        canvas.addPlanet(new Planet(1000000, 0, 0, 0, 0));
//        canvas.addPlanet(new Planet(200, Math.sqrt(1.0/30), 0, 0, 300));
//        canvas.addPlanet(new Planet(1, Math.sqrt(1.0/30) + Math.sqrt(0.000666), 0, 0, 303));

        // 2 Center suns and 1 planet
//        canvas.addPlanet(new Planet(100000, Math.sqrt(1.0/100), 0, 0, 25));
//        canvas.addPlanet(new Planet(100000, -Math.sqrt(1.0/100), 0, 0, -25));
//        canvas.addPlanet(new Planet(1, Math.sqrt(2.0/300), 0, 0, 300));

        // Binary star system with a planet orbiting each star and exoplanet and exoplanet moon
//        canvas.addPlanet(new Planet(100000, Math.sqrt(1.0/800), 0, 0, 200));
//        canvas.addPlanet(new Planet(100000, -Math.sqrt(1.0/800), 0, 0, -200));
//        canvas.addPlanet(new Planet(1, Math.sqrt(1.0/25) + Math.sqrt(1.0/800), 0, 0, 225));
//        canvas.addPlanet(new Planet(1, -Math.sqrt(1.0/25) - Math.sqrt(1.0/800), 0, 0, -225));
//        canvas.addPlanet(new Planet(200, Math.sqrt(1.0/1000), 0, 0, 2000));
//        canvas.addPlanet(new Planet(1, Math.sqrt(1.0/1000) + Math.sqrt(0.00066666), 0, 0, 2003));

        // Earth Sun Moon representation, ratio of revolution time, ratio of orbit distance, mass ratios are all correct
        // Planet size is Not correct
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

    public static void main(String[] args) {
        // Initialize window with size and close functionality
        PlanetDraw window = new PlanetDraw();
        window.setBounds(0, 0, 800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}