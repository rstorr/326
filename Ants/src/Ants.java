import java.util.*;
import java.awt.*;

/**
 * Ants class simulates (some of) the behaviour of creatures related
 * to Langton’s ant. The ant moves on a plane according to its input,
 * the DNA, and the ouput is its final location after completing x steps.
 *
 * @author Reuben Storr, Bayley Millar.
 */
public class Ants {
    private static ArrayList<String> DNA = new ArrayList<>();

    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            final String line = sc.nextLine();
            if (isInteger(line)) {
                scenario(DNA, Integer.parseInt(line));
            } else if (!line.startsWith("#") && !line.isEmpty()) {
                DNA.add(line);
            }
        }
    }

    private static boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static int getCompassInt(final char dir) {
        switch (dir) {
            case 'N':
                return 0;
            case 'E':
                return 1;
            case 'S':
                return 2;
            case 'W':
                return 3;
            default:
                return 0;
        }
    }

    private static void scenario(final ArrayList<String> DNA, final int totalSteps) {
        LinkedHashMap<String, Character> plane = new LinkedHashMap<>();
        final Point ant = new Point();
        int count = 0;
        int cameFrom = getCompassInt('S');
        char nextDir = '\0';
        char newState = '\0';

        while (count < totalSteps) {
            final String key = ant.getX() + " " + ant.getY();
            if (plane.get(key) != null) {
                final char curState = plane.get(key);
                for (String dnaLine : DNA) {
                    if (curState == dnaLine.charAt(0)) {
                        nextDir = dnaLine.substring(2, 6).charAt(cameFrom);
                        newState = dnaLine.substring(7, 11).charAt(cameFrom);
                    }
                }
            } else {
                nextDir = DNA.get(0).substring(2, 6).charAt(cameFrom);
                newState = DNA.get(0).substring(7, 11).charAt(cameFrom);
            }

            cameFrom = getCompassInt(nextDir);
            plane.put(key, newState);
            moveAnt(ant, nextDir);
            count++;
        }
        printScenario(DNA, totalSteps, ant);
    }

    private static void moveAnt(final Point ant, final char dir) {
        switch (dir) {
            case 'N':
                ant.translate(0, 1);
                break;
            case 'E':
                ant.translate(1, 0);
                break;
            case 'S':
                ant.translate(0, -1);
                break;
            case 'W':
                ant.translate(-1, 0);
                break;
        }
    }

    private static void printScenario(final ArrayList<String> DNA, final int totalSteps,
                                      final Point ant) {
        DNA.forEach(System.out::println);
        System.out.println(totalSteps);
        System.out.println("# " + (int) ant.getX() + " " + (int) ant.getY() + "\n");
        DNA.clear();
    }
}
