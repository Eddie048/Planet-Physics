import java.awt.Graphics;
import java.util.List;


public class Planet {
    private final double mass;
    private double velocityX;
    private double velocityY;
    private double positionX;
    private double positionY;

    private final double GRAVITY;

    public Planet(double m, double vX, double vY, int xVal, int yVal) {
        mass = m;
        velocityX = vX;
        velocityY = vY;
        positionX = xVal;
        positionY = yVal;
        GRAVITY = 0.0001;
    }

    public double getMass() {
        return mass;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void update(List<Planet> planets) {

        // Calculate the force on this planet from all the other planets
        double totalForceX = 0;
        double totalForceY = 0;

        for(Planet p : planets) {
            // Skip if this is itself
            if (p.equals(this)) continue;

            // Calculate the x and y force on this planet from the planet p
            double forceX = Math.abs(p.getMass() / Math.sqrt(Math.pow(positionX - p.getPositionX(), 2) +
                    Math.pow(positionY - p.getPositionY(), 2)) *
                    Math.cos(Math.atan((positionY - p.getPositionY())/(positionX - p.getPositionX()))));

            double forceY = Math.abs(p.getMass() / Math.sqrt(Math.pow(positionX - p.getPositionX(), 2) +
                    Math.pow(positionY - p.getPositionY(), 2)) *
                    Math.sin(Math.atan((positionY - p.getPositionY())/(positionX - p.getPositionX()))));

            // Flip the sign if needed
            if(positionX > p.getPositionX()) forceX *= -1;
            if(positionY > p.getPositionY()) forceY *= -1;

            // Update force value
            totalForceX += forceX;
            totalForceY += forceY;
        }

        velocityX += GRAVITY*totalForceX;
        velocityY += GRAVITY*totalForceY;

        positionX += velocityX;
        positionY += velocityY;
    }

    public void draw(Graphics g, int shiftX, int shiftY, double zoom) {
        int radius = (int) Math.pow(mass, 1.0/3) + 2;

        g.fillOval((int)(positionX/zoom) + 400 + shiftX - radius/2,
                (int)(positionY/zoom) + 400 + shiftY - radius/2, radius, radius);
    }

    public String toString() {
        return "Mass: " + mass + " Position: " + positionX + ", " + positionY + " Velocity: " + velocityX +", " + velocityY;
    }
}
