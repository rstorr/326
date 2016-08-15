
import javax.swing.*;
import java.awt.*;
import java.util.*;

class Toothpicks extends JFrame {
    private final static int GRID_SIZE = 500;
    private final static int LINE_LENGTH = 10;
    private int generations;

    public Toothpicks(int generations){
        this.generations = generations;
        JPanel panel=new JPanel();
        getContentPane().add(panel);
        setSize(GRID_SIZE, GRID_SIZE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        generateLines(g);
    }

    private void generateLines(Graphics g) {
        final Queue endPoints = new LinkedList();
        int generationsCreated = 0;
        int linesInGeneration = 2;
        boolean drawVerticle = true;

        g.drawLine(GRID_SIZE/2 - LINE_LENGTH/2, GRID_SIZE/2,
                GRID_SIZE/2 + LINE_LENGTH/2, GRID_SIZE/2);
        endPoints.add(new Point(GRID_SIZE/2 - LINE_LENGTH/2, GRID_SIZE/2));
        endPoints.add(new Point(GRID_SIZE/2 + LINE_LENGTH/2, GRID_SIZE/2));

        while (generationsCreated < generations) {
            int linesDrawn = 0;
            while (linesDrawn < linesInGeneration){
                final Point p = (Point) endPoints.remove();
                if (drawVerticle){
                    g.drawLine(p.x, p.y, p.x, p.y - LINE_LENGTH/2);
                    g.drawLine(p.x, p.y, p.x, p.y + LINE_LENGTH/2);
                    endPoints.add(new Point(p.x, p.y - LINE_LENGTH/2));
                    endPoints.add(new Point(p.x, p.y + LINE_LENGTH/2));
                } else {
                    g.drawLine(p.x, p.y, p.x - LINE_LENGTH/2, p.y);
                    g.drawLine(p.x, p.y, p.x + LINE_LENGTH/2, p.y);
                    endPoints.add(new Point(p.x - LINE_LENGTH/2, p.y));
                    endPoints.add(new Point(p.x + LINE_LENGTH/2, p.y));
                }
                linesDrawn++;
            }
            linesInGeneration*=2;
            drawVerticle = !drawVerticle;
            generationsCreated++;
        }
    }

    public static void main(String []args){
        Toothpicks s=new Toothpicks(Integer.valueOf(args[0]));
        s.setVisible(true);
    }
}