import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * The canvas that holds planets
 */
public class PlanetCanvas extends JPanel {

    private final ArrayList<Planet> planets;
    private double shiftX, shiftY;
    private double zoom;

    private int followPlanet;

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

        // Initialize follow planet to -1
        followPlanet = -1;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) shiftY += 100;
                else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) shiftX += 100;
                else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) shiftY -= 100;
                else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) shiftX -= 100;
                else if (key == KeyEvent.VK_1) zoom *= 2;
                else if (key == KeyEvent.VK_2) zoom /= 2;

                repaint();
            }
        });
    }

    /**
     * Add a planet at a specified location to the canvas
     * @param x x coordinate of the planet
     * @param y y coordinate of the planet
     */
    public void addPlanet(int x, int y, int mass) {
        planets.add(new Planet(mass, 0, 0, (x - getWidth()/2.0) * zoom - shiftX, (y - getHeight()/2.0) * zoom - shiftY));
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
            int radius = (int) planet.getRadius();
            int xCoordinate = (int)((planet.getPositionX() + shiftX)/zoom + getWidth()/2.0 - radius);
            int yCoordinate = (int)((planet.getPositionY() + shiftY)/zoom + getHeight()/2.0 - radius);

            g.fillOval(xCoordinate, yCoordinate, radius*2, radius*2);
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

        if (followPlanet == -1) return;

        shiftX = -planets.get(followPlanet).getPositionX();
        shiftY = -planets.get(followPlanet).getPositionY();
    }

    /**
     * Change planet to follow, loop through list of planets
     */
    public void setFollowPlanet() {
        followPlanet ++;
        if (followPlanet >= planets.size()) followPlanet = 0;
    }

    /**
     * Change planet to follow to -1, allow free movement
     */
    public void stopFollowing() {
        followPlanet = -1;
    }
}