import java.awt.Graphics;
import java.util.List;


public class Planet {
    private final double mass;
    private double velocityX;
    private double velocityY;
    private double positionX;
    private double positionY;

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

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

            // Get distances
            double distX = positionX - p.getPositionX();
            double distY = positionY - p.getPositionY();
            double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));

            // Calculate the x and y force on this planet from the planet p

            double forceX = Math.abs(p.getMass() / distance * Math.cos(Math.atan(distY/distX)));

            double forceY = Math.abs(p.getMass() / distance * Math.sin(Math.atan(distY/distX)));

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
