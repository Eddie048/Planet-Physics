import java.util.List;


public class Planet {
    private final double mass;
    private final double radius;
    private double velocityX;
    private double velocityY;
    private double positionX;
    private double positionY;

    private final double GRAVITY;

    public Planet(double m, double vX, double vY, double xVal, double yVal) {
        mass = m;
        radius = (Math.pow(mass, 1.0/4) + 2) / 2;
        velocityX = vX;
        velocityY = vY;
        positionX = xVal;
        positionY = yVal;
        GRAVITY = 0.00001;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void update(List<Planet> planets) {

        // Calculate the acceleration on this planet from all the other planets
        double totalAccelerationX = 0;
        double totalAccelerationY = 0;

        for(Planet p : planets) {
            // Skip if this is itself
            if (p.equals(this)) continue;

            // Get distances
            double distX = positionX - p.getPositionX();
            double distY = positionY - p.getPositionY();
            double distanceSquared = Math.pow(distX, 2) + Math.pow(distY, 2);

            // Calculate the total acceleration using the equation a=Gm/r^2
            double acceleration = GRAVITY * p.getMass() / distanceSquared;

            // Calculate the x and y acceleration using trigonometry
            double angle = Math.atan(distY/distX);
            double accelerationX = acceleration * Math.abs(Math.cos(angle));
            double accelerationY = acceleration * Math.abs(Math.sin(angle));

            // Flip the sign if needed
            if(positionX > p.getPositionX()) accelerationX *= -1;
            if(positionY > p.getPositionY()) accelerationY *= -1;

            // Update total acceleration accumulators
            totalAccelerationX += accelerationX;
            totalAccelerationY += accelerationY;
        }

        // Update velocity and position variables
        velocityX += totalAccelerationX;
        velocityY += totalAccelerationY;

        positionX += velocityX;
        positionY += velocityY;
    }

    public String toString() {
        return "Mass: " + mass + " Position: " + positionX + ", " + positionY + " Velocity: " + velocityX +", " + velocityY;
    }
}
