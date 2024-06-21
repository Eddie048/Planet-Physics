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

    private boolean isNormalMode;

    private double fovBottom;
    private double fovTop;

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

        // Bottom and top of viewpoints, measured in radians, in the range -pi to pi
        fovBottom = -0.25 * Math.PI;
        fovTop = 0.25 * Math.PI;

        // Initialize follow planet to -1
        followPlanet = -1;

        // Initialize viewMode
        isNormalMode = true;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                switch (key) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        if (isNormalMode) shiftY += 100;
                        else {
                            fovTop -= (0.125 * Math.PI);
                            fovBottom -= (0.125 * Math.PI);
                        }
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        if (isNormalMode) shiftX += 100;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        if (isNormalMode) shiftY -= 100;
                        else {
                            fovTop += (0.125 * Math.PI);
                            fovBottom += (0.125 * Math.PI);
                        }
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        if (isNormalMode) shiftX -= 100;
                        break;
                    case KeyEvent.VK_1:
                        if (isNormalMode) zoom *= 2;
                        else {
                            fovTop += 0.25;
                            fovBottom -= 0.25;
                        }
                        break;
                    case KeyEvent.VK_2:
                        if (isNormalMode) zoom /= 2;
                        else {
                            fovTop -= 0.25;
                            fovBottom += 0.25;
                        }
                        break;
                }

                // Ensure fovTop and fovBottom are in the correct range
                fovTop = standardizeAngle(fovTop);
                fovBottom = standardizeAngle(fovBottom);

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

        if (isNormalMode) {
            for (Planet planet : planets)
            {
                int radius = (int) (planet.getRadius()/zoom);
                int xCoordinate = (int)((planet.getPositionX() + shiftX)/zoom + getWidth()/2.0 - radius);
                int yCoordinate = (int)((planet.getPositionY() + shiftY)/zoom + getHeight()/2.0 - radius);

                g.fillOval(xCoordinate, yCoordinate, radius*2, radius*2);

                g.setColor(Color.RED);
                g.fillRect(xCoordinate - 25, yCoordinate + 1 + radius, 20, 2);
                g.setColor(Color.WHITE);
            }
        } else {
            // Set X and Y coordinates of viewPoint to followPlanet, or 0, 0 if that is not set
            double viewX, viewY;

            if (followPlanet == -1) {
                viewX = 0;
                viewY = 0;
            } else {
                viewX = planets.get(followPlanet).getPositionX();
                viewY = planets.get(followPlanet).getPositionY();
            }

            for (Planet planet : planets) {
                // Skip planet we are currently following
                if (followPlanet != -1 && planets.get(followPlanet).equals(planet)) continue;

                // Normalize coordinates by getting coordinates relative to the viewpoint
                double xRelative = planet.getPositionX() - viewX;
                double yRelative = planet.getPositionY() - viewY;

                // Get relative angle from view planet
                double viewAngle = Math.atan(yRelative / xRelative);
                // Fix tangent flaws, ensure on same scale as fovTop and fovBottom
                if (xRelative < 0) viewAngle += Math.PI;
                viewAngle = standardizeAngle(viewAngle);

                // Skip planet if not within fovTop and fovBottom
                if (fovTop > fovBottom) {
                    if (viewAngle < fovBottom || viewAngle > fovTop) continue;
                } else if (viewAngle < fovBottom && viewAngle > fovTop) continue;
                // TODO: Add planet radius into equation

                // Get position relative to bottom of screen, check for case that bottom is above top
                double angleToPixelConversion = getHeight() / (fovTop > fovBottom ?  fovTop - fovBottom : fovTop - fovBottom + 2*Math.PI);

                //Draw planet in correct spot, correcting for break in domain
                double relativeAngle = viewAngle > fovBottom ? viewAngle - fovBottom : viewAngle - fovBottom + 2*Math.PI;
                double yDraw = relativeAngle * angleToPixelConversion;

                // Calculate the percent of the screen the planet takes up
                double radius = planet.getRadius();
                double distance = Math.sqrt(xRelative * xRelative + yRelative * yRelative);
                double angle = Math.atan(radius/distance);
                double newRadius = angle * angleToPixelConversion;

                // Draw planet at calculated position
                g.fillOval((int) (getWidth()/2 - newRadius), (int) (yDraw - newRadius), (int) (newRadius*2), (int) (newRadius*2));

                // Draw line for better visibility
                g.setColor(Color.RED);
                g.fillRect(getWidth()/2 - 40, (int) yDraw - 1, 20, 2);
                g.setColor(Color.WHITE);
            }
        }
    }

    /**
     * Update all the planets
     */
    public void updatePlanets() {
        for(Planet planet : planets) {
            planet.update(planets);
        }
        if (followPlanet != -1) {
            shiftX = -planets.get(followPlanet).getPositionX();
            shiftY = -planets.get(followPlanet).getPositionY();
        };
        repaint();
    }

    /**
     * Change planet to follow, loop through list of planets
     */
    public void setFollowPlanet() {
        followPlanet ++;
        if (followPlanet >= planets.size()) followPlanet = 0;
        shiftX = -planets.get(followPlanet).getPositionX();
        shiftY = -planets.get(followPlanet).getPositionY();
        repaint();
    }

    /**
     * Change planet to follow to -1, allow free movement
     */
    public void stopFollowing() {
        followPlanet = -1;
    }

    /**
     * Toggle viewMode from normal to planet and back
     */
    public void toggleViewMode() {
        isNormalMode = !isNormalMode;
        repaint();
    }

    /**
     * Takes in an angle in radians and returns an angle in the range -PI to PI
     * @param angle the angle to standardize
     * @return standardized angle
     */
    public double standardizeAngle(double angle) {
        if (angle > Math.PI) angle -= 2*Math.PI;
        if (angle <= -Math.PI) angle += 2*Math.PI;

        return angle;
    }
}