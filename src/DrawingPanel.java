import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent; //???
import java.awt.event.KeyEvent; //????
import javax.swing.JPanel;
import javax.swing.JColorChooser;
import java.util.ArrayList;



public class DrawingPanel extends JPanel
{
    private ArrayList<Planet> planets;
    private Color color;

    private int shiftX;
    private int shiftY;
    private double zoom;
    // Constructor:
    public DrawingPanel()
    {
        color = Color.BLACK;

        setBackground(color);

        planets = new ArrayList<Planet>();

        shiftX = 0;
        shiftY = 0;
        zoom = 1;
    }

    public Color getColor() {
        return color;
    }

    // Called from controlPanel when the "Add balloon" button is clicked.
    // Places a new balloon with a random radius and the current color
    // at the center of the drawing panel.
    public void addPlanet(int p)
    {
        switch (p){
            case 1: planets.add(new Planet(10000, 0, 0, -300, -300)); break;
            case 2: planets.add(new Planet(100, -2, -2, 200, 100)); break;
            case 3: planets.add(new Planet(1000, 0.7, 0, 0, 200)); break;
            case 4: planets.add(new Planet(20, 0.5, 0, 0, 400)); break;
            case 5: planets.add(new Planet(10, 0, 0.5, -400, 0)); break;
        }

        repaint();
    }

    public void addPlanet(Planet p)
    {
        planets.add(p);
        repaint();
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        for (Planet p : planets)
        {
            p.draw(g, shiftX, shiftY, zoom);
            System.out.println(p);

        }
        System.out.println();

    }

    public void updatePlanets(){
        for(Planet pOriginal : planets){
            Planet[] pList = new Planet[planets.size() - 1];
            int i = 0;

            for(Planet p2 : planets){
                if (pOriginal != p2){
                    pList[i] = p2;
                    i++;
                }
            }

            pOriginal.update(pList);

        }
        System.out.println("repainting");
        repaint();
    }

    public void moveView(int index) {
        switch (index){
            case 1: shiftY += 200; break;
            case 2: shiftY -= 200; break;
            case 3: shiftX += 200; break;
            case 4: shiftX -= 200; break;
            case 5: zoom *= 2; break;
            case 6: zoom /= 2; break;
        }

        repaint();
    }

}