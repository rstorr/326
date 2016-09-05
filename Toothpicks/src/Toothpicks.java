
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
    private static int GRID_SIZE = 350;
    private int lineLength;
    private Double ratio = 1.0;
    private int generations;

    private Toothpicks(int generations, Double ratio){
        this.ratio = ratio;
        this.generations = generations;

        int lineLength = 50;
        final int[] drawingDimensions = simulation(lineLength);
        double diff;
        if (drawingDimensions[0] > drawingDimensions[1]){
            diff = (double) GRID_SIZE / drawingDimensions[0];
        } else {
            diff = (double) GRID_SIZE / drawingDimensions[1];
        }

        this.lineLength = (int) Math.round((lineLength * diff) - 0.5);

        JPanel panel=new JPanel();
        getContentPane().add(panel);
        setSize(GRID_SIZE, GRID_SIZE);
    }

    private Toothpicks(int generations){
        this.generations = generations;

        int lineLength = 50;
        final int[] drawingDimensions = simulation(lineLength);
        double diff;
        if (drawingDimensions[0] > drawingDimensions[1]){
            diff = (double) GRID_SIZE / drawingDimensions[0];
        } else {
            diff = (double) GRID_SIZE / drawingDimensions[1];
        }

        this.lineLength = (int) Math.round((lineLength * diff) - 0.5);

        JPanel panel=new JPanel();
        getContentPane().add(panel);
        setSize(GRID_SIZE, GRID_SIZE);
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

    private void generateLines(Graphics g) {
        final Queue<Point> endPoints = new LinkedList<>();
        int generationsCreated = 0;
        int linesInGeneration = 2;
        boolean drawVerticle = true;

        g.drawLine(GRID_SIZE/2 - lineLength /2, GRID_SIZE/2,
                GRID_SIZE/2 + lineLength /2, GRID_SIZE/2);
        endPoints.add(new Point(GRID_SIZE/2 - lineLength /2, GRID_SIZE/2));
        endPoints.add(new Point(GRID_SIZE/2 + lineLength /2, GRID_SIZE/2));

        while (generationsCreated < generations) {
            int linesDrawn = 0;
            lineLength*=ratio;
            while (linesDrawn < linesInGeneration){
                final Point p = endPoints.remove();
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

    private int[] simulation(int lineLength){
        final Queue<Point> endPoints = new LinkedList<>();
        int generationsCreated = 0;
        int linesInGeneration = 2;
        boolean drawVerticle = true;

        int x_min = GRID_SIZE/2 - lineLength /2;
        int x_max = GRID_SIZE/2 + lineLength /2;
        int y_min = GRID_SIZE/2;
        int y_max = GRID_SIZE/2;

        endPoints.add(new Point(x_min, y_min));
        endPoints.add(new Point(x_max, y_max));

        while (generationsCreated < generations) {
            int linesDrawn = 0;
            lineLength*=ratio;
            while (linesDrawn < linesInGeneration){
                final Point p = endPoints.remove();
                if (drawVerticle){
                    int y_start = p.y - lineLength /2;
                    int y_finish = p.y + lineLength /2;

                    if (y_start < y_min) y_min = y_start;
                    if (y_finish > y_max) y_max = y_finish;

                    endPoints.add(new Point(p.x, y_start));
                    endPoints.add(new Point(p.x, y_finish));
                } else {
                    int x_start = p.x - lineLength /2;
                    int x_finish = p.x + lineLength /2;

                    if (x_start < x_min) x_min = x_start;
                    if (x_finish > x_max) x_max = x_finish;

                    endPoints.add(new Point(p.x - lineLength /2, p.y));
                    endPoints.add(new Point(p.x + lineLength /2, p.y));
                }
                linesDrawn++;
            }
            linesInGeneration*=2;
            drawVerticle = !drawVerticle;
            generationsCreated++;
        }

        return new int[]{x_max - x_min, y_max - y_min};
    }
}