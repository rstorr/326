
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Toothpicks accepts the number N as its command line argument
 * and produces a representation of an Nth generation toothpick
 * diagram. Optionally, it takes a double that is the ratio of the
 * length of toothpicks in each succeeding generation.
 *
 * @author Reuben Storr, Bayley Millar
 */
class Toothpicks extends JFrame {
    private static int lineLength = 50;
    private static int GRID_X = lineLength;
    private static int GRID_Y = lineLength;
    private Double ratio = 1.0;
    private int generations;

    public Toothpicks(int generations, Double ratio){
        this.ratio = ratio;
        this.generations = generations;
        JPanel panel=new JPanel();
        getContentPane().add(panel);

        windowSize();
        setSize(GRID_X, GRID_Y);
    }

    public Toothpicks(int generations){
        this.generations = generations;
        JPanel panel=new JPanel();
        getContentPane().add(panel);

        windowSize();
        setSize(GRID_X, GRID_Y);
    }

    public static void main(String []args){
        final Toothpicks s;
        if (args.length == 2){
            s = new Toothpicks(Integer.valueOf(args[0]),
                    Double.valueOf(args[1]));
        } else {
            s = new Toothpicks(Integer.valueOf(args[0]));
        }
        s.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        generateLines(g);
    }

    private void windowSize(){
        double temp = lineLength;
        for(int i = 1; i < generations ; i++){
            temp = temp*ratio;
            if(i % 2 == 0){
                GRID_Y += temp;
            } else {
                GRID_X += temp;
            }
        }
    }

    private void generateLines(Graphics g) {
        final Queue<Point> endPoints = new LinkedList<Point>();
        int generationsCreated = 0;
        int linesInGeneration = 2;
        boolean drawVerticle = true;
        g.drawLine(GRID_X/2 - lineLength /2, GRID_Y/2,
                GRID_X/2 + lineLength /2, GRID_Y/2);
        endPoints.add(new Point(GRID_X/2 - lineLength /2, GRID_Y/2));
        endPoints.add(new Point(GRID_X/2 + lineLength /2, GRID_Y/2));

        while (generationsCreated < generations) {
            int linesDrawn = 0;
            lineLength*=ratio;
            while (linesDrawn < linesInGeneration){
                final Point p = (Point) endPoints.remove();
                if (drawVerticle){
                    g.drawLine(p.x, p.y, p.x, p.y - lineLength /2);
                    g.drawLine(p.x, p.y, p.x, p.y + lineLength /2);
                    endPoints.add(new Point(p.x, p.y - lineLength /2));
                    endPoints.add(new Point(p.x, p.y + lineLength /2));
                } else {
                    g.drawLine(p.x, p.y, p.x - lineLength /2, p.y);
                    g.drawLine(p.x, p.y, p.x + lineLength /2, p.y);
                    endPoints.add(new Point(p.x - lineLength /2, p.y));
                    endPoints.add(new Point(p.x + lineLength /2, p.y));
                }
                linesDrawn++;
            }
            linesInGeneration*=2;
            drawVerticle = !drawVerticle;
            generationsCreated++;
        }
    }
}