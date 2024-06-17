import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class PlanetDraw extends JFrame
{
    public PlanetDraw()  // constructor
    {
        super("Drawing Editor");

        DrawingPanel canvas = new DrawingPanel();
        ControlPanel controls = new ControlPanel(canvas);
        Container c = getContentPane();
        c.add(canvas, BorderLayout.CENTER);
        c.add(controls, BorderLayout.SOUTH);

        canvas.addPlanet(new Planet(100, 1, 0, 1, 50));
        canvas.addPlanet(new Planet(10000, 0, 0, 0, 0));
        canvas.addPlanet(new Planet(100, -1, 0, -1, -50));

        //stable orbit
        //canvas.addPlanet(new Planet(10, 35, 0, 300, 100));
        //canvas.addPlanet(new Planet(10000, 0, 0, 299, 300));
    }

    public static void main(String[] args)
    {
        PlanetDraw window = new PlanetDraw();
        window.setBounds(0, 0, 800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);


    }
}