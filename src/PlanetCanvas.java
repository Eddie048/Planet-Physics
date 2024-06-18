import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;
// TODO: use import java.awt.event.MouseEvent; to create planets on click

/**
 * The canvas that holds planets
 */
public class PlanetCanvas extends JPanel {

    private final ArrayList<Planet> planets;
    private int shiftX, shiftY;
    private double zoom;

    public PlanetCanvas() {

        // Set background color of canvas
        Color color = Color.BLACK;
        setBackground(color);

        // Initialize planet list
        planets = new ArrayList<>();

        // Initialize view variables
        shiftX = 0;
        shiftY = 0;
        zoom = 1;
    }

    /**
     * Add a planet of a specified type to the canvas
     * @param type the type of planet to add
     */
    public void addPlanet(int type) {
        switch (type){
            case 0: planets.add(new Planet(10000, 0, 0, -300, -300)); break;
            case 1: planets.add(new Planet(100, -2, -2, 200, 100)); break;
            case 2: planets.add(new Planet(1000, 0.7, 0, 0, 200)); break;
            case 3: planets.add(new Planet(20, 0.5, 0, 0, 400)); break;
            case 4: planets.add(new Planet(10, 0, 0.5, -400, 0)); break;
        }
        repaint();
    }

    /**
     * Add a custom planet to the canvas
     * @param planet the planet to add
     */
    public void addPlanet(Planet planet) {
        planets.add(planet);
        repaint();
    }

    /**
     * Draw the planets on the canvas
     * @param g graphics
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        for (Planet planet : planets)
        {
            planet.draw(g, shiftX, shiftY, zoom);
        }
    }

    /**
     * Update all the planets
     */
    public void updatePlanets() {
        for(Planet planet : planets) {
            planet.update(planets);
        }
        repaint();
    }

    /**
     * Move the viewpoint in a specified way
     * @param code how to move the viewpoint
     */
    public void moveView(int code) {
        switch (code) {
            case 0:
                shiftY += 200;
                break;
            case 1:
                shiftY -= 200;
                break;
            case 2:
                shiftX += 200;
                break;
            case 3:
                shiftX -= 200;
                break;
            case 4:
                zoom *= 2;
                break;
            case 5:
                zoom /= 2;
                break;
        }
        repaint();
    }
}