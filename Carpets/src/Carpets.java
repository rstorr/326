import java.util.*;

/**
 * Given a stock of carpet strips and a carpet type Carpets class will
 * output a carpet by sewing together the strips that is of the
 * specified carpet type.
 *
 * @author Reuben Storr, Joe Benn, Bayley Millar, George Bonnici-Carter, Blake Carter
 */
public class Carpets {
    final private static int NO_MATCHES = 0;
    final private static int MAX_MATCHES = 1;
    final private static int BALANCED_MATCHES = 2;
    final private static ArrayList<char[]> inputCarpetStrips = new ArrayList<>();

    private static int stripLength = 0;
    private static int curMatches = 0;
    private static ArrayList<char[]> correctCarpet;
    private static int carpetSize;
    private static int carpetType;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        carpetSize = Integer.valueOf(args[1]);

        if (sc.hasNextLine()){
            final String line = sc.nextLine();
            stripLength = line.length();
            inputCarpetStrips.add(line.toCharArray());

        }
        while (sc.hasNextLine()) {
            final String line = sc.nextLine();

            if (line.length() == 0){
                break;
            } else if (line.length() != stripLength){
                System.out.println("All strips must be of same length.");
            } else {
                inputCarpetStrips.add(line.toCharArray());
            }
        }

        if (inputCarpetStrips.size() * stripLength >= carpetSize) {
            switch (args[0]) {
                case "-n":
                    carpetType = NO_MATCHES;
                    break;
                case "-m":
                    carpetType = MAX_MATCHES;
                    break;
                case "-b":
                    carpetType = BALANCED_MATCHES;
                    break;
            }

            generateCarpets(inputCarpetStrips, new Stack<>());

            if (correctCarpet == null) {
                System.out.println("not possible");
            } else {
                for (char[] strip : correctCarpet) {
                    for (char c : strip) {
                        System.out.print(c);
                    }
                    System.out.println();
                }

                if (carpetType == MAX_MATCHES){
                    System.out.println(curMatches);
                } else if (carpetType == BALANCED_MATCHES){
                    System.out.println(
                            Math.abs((curMatches) - (carpetSize - curMatches*2)));
                }
            }
        } else {
            System.out.println("Carpet size requested larger than stock.");
        }
    }

    private static void generateCarpets(ArrayList<char[]> strips, Stack<char[]> stack) {
        if(stack.size() * stripLength == carpetSize) {
            final ArrayList<char[]> carpet = new ArrayList<>(stack);
            final int matches = getMatches(carpet);
            if (carpetType == NO_MATCHES) {
                if (matches == 0) {
                    correctCarpet = carpet;
                }
            } else if (carpetType == MAX_MATCHES) {
                if (matches > curMatches) {
                    curMatches = matches;
                    correctCarpet = carpet;
                }
            } else if (carpetType == BALANCED_MATCHES) {
                final int middle = carpetSize / 2;
                if (Math.abs(middle - matches) <
                        Math.abs(middle - curMatches)) {
                    curMatches = matches;
                    correctCarpet = carpet;
                }
            }

        }

        final ArrayList<char[]> availableItems = new ArrayList<>(strips);
        for(char[] strip : availableItems) {
            stack.push(strip);
            strips.remove(strip);
            generateCarpets(strips, stack);
            strips.add(stack.pop());
        }
    }

    private static int getMatches(ArrayList<char[]> carpetStrips){
        int matches = 0;

        for (int i = 0; i < carpetStrips.size() - 1; i++){
            for(int j = 0; j < stripLength; j++){
                if (carpetStrips.get(i)[j] == carpetStrips.get(i+1)[j]){
                   matches++;
                }
            }
        }

        return matches;
    }
}
