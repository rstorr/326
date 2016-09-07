import java.util.ArrayList;
import java.util.Scanner;

public class Peanuts_And_Pretzels {

    public static void main (String[] args){
        final Scanner sc = new Scanner(System.in);
        boolean newScenario = true;

        while (sc.hasNextLine()){
            final ArrayList<String> peanutRules = new ArrayList<>();
            final ArrayList<String> pretzelRules = new ArrayList<>();
            final String[] splitLine = sc.nextLine().split(" ");
            int numPeanuts = 0;
            int numPretzels = 0;

            if (newScenario){
                numPeanuts = Integer.valueOf(splitLine[0]);
                numPretzels = Integer.valueOf(splitLine[1]);
                if (numPeanuts > 1000 || numPretzels > 1000){
                    System.out.println("Error: number of peanuts or " +
                            "pretzels cannot be > 1000.");
                } else {
                    newScenario = false;
                }
            } else if (splitLine.length == 0 || !sc.hasNextLine()){
                snackChoice(numPeanuts, numPretzels, peanutRules, pretzelRules);
                newScenario = true;
                peanutRules.clear();
                pretzelRules.clear();
            } else {
                peanutRules.add(splitLine[0]);
                pretzelRules.add(splitLine[1]);
            }
        }
    }

    private static void snackChoice(final int numPeanuts, final int numPretzels,
                             final ArrayList<String> peanutRules, final ArrayList<String> pretzelRules){
        
    }
}
