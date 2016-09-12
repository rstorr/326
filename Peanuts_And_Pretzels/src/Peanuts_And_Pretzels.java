
import java.util.*;

public class Peanuts_And_Pretzels {
    private final static ArrayList<Integer> peanutMoveList = new ArrayList<>();
    private final static ArrayList<Integer> pretzelMoveList = new ArrayList<>();
    private static int numPeanuts;
    private static int numPretzels;

    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        boolean newScenario = true;

        while (sc.hasNextLine()) {
            final String[] splitLine = sc.nextLine().split(" ");

            if (newScenario) {
                numPeanuts = Integer.valueOf(splitLine[0]);
                numPretzels = Integer.valueOf(splitLine[1]);

                peanutMoveList.add(0);
                pretzelMoveList.add(1);
                peanutMoveList.add(1);
                pretzelMoveList.add(0);

                if (numPeanuts > 1000 || numPretzels > 1000) {
                    System.out.println("Error: number of peanuts or " +
                            "pretzels cannot be > 1000.");
                } else {
                    newScenario = false;
                }
            } else if (splitLine.length == 0 || !sc.hasNextLine()) {
                removeInvalidMoves();

                for (int i = 0; i < peanutMoveList.size(); i++) {
                    int peanutMove = peanutMoveList.get(i);
                    int pretzelMove = pretzelMoveList.get(i);
                    if (outcomeOfSnackChoice(peanutMove,
                            pretzelMove,
                            numPeanuts,
                            numPretzels,
                            true)) {
                        System.out.println(peanutMove + " " + pretzelMove);
                        break;
                    }
                }

                newScenario = true;
                peanutMoveList.clear();
                pretzelMoveList.clear();
            } else {
                addAllPossibleMoves(splitLine[0], splitLine[1]);
            }
        }
    }

    private static void removeInvalidMoves() {
        for (int i = 0; i < peanutMoveList.size(); i++) {
            if (peanutMoveList.get(i) == 0 && pretzelMoveList.get(i) == 0) {
                peanutMoveList.remove(i);
                pretzelMoveList.remove(i);
            }
        }
    }

    private static boolean outcomeOfSnackChoice(final int curPeanutMove, final int curPretzelMove,
                                                final int peanutsLeft, final int pretzelsLeft,
                                                final boolean wasUsersTurn) {

        if (peanutsLeft < 1 && pretzelsLeft < 1 && wasUsersTurn) {
            return false;
        }

        if (peanutsLeft < 1 && pretzelsLeft < 1 && !wasUsersTurn) {
            return true;
        }

        int peanutsAfterTurn = peanutsLeft - curPeanutMove;
        int pretzelsAfterTurn = pretzelsLeft - curPretzelMove;

        if (wasUsersTurn) {
            for (int i = 0; i < peanutMoveList.size(); i++) {
                int peanutMove = peanutMoveList.get(i);
                int pretzelMove = pretzelMoveList.get(i);
                if (peanutsAfterTurn - peanutMove > -1 && pretzelsAfterTurn - pretzelMove > -1) {
                    if (!outcomeOfSnackChoice(
                            peanutMove,
                            pretzelMove,
                            peanutsAfterTurn,
                            pretzelsAfterTurn,
                            false)
                            ) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            for (int i = 0; i < peanutMoveList.size(); i++) {
                int peanutMove = peanutMoveList.get(i);
                int pretzelMove = pretzelMoveList.get(i);
                if (peanutsAfterTurn - peanutMove > -1 && pretzelsAfterTurn - pretzelMove > -1) {
                    if (outcomeOfSnackChoice(
                            peanutMove,
                            pretzelMove,
                            peanutsAfterTurn,
                            pretzelsAfterTurn,
                            true)
                            ) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static void addAllPossibleMoves(String peanutRule, String pretzelRule) {
        final String operators = String.valueOf(peanutRule.charAt(0)) +
                String.valueOf(pretzelRule.charAt(0));
        final int peanutNum = Character.getNumericValue(peanutRule.charAt(1));
        final int pretzelNum = Character.getNumericValue(pretzelRule.charAt(1));
        ArrayList<Integer> arr;
        ArrayList<Integer> arr2;

        switch (operators) {
            case "==":
                peanutMoveList.add(peanutNum);
                pretzelMoveList.add(pretzelNum);
                break;
            case "<=":
                arr = numbersLessThan(peanutNum);
                peanutMoveList.addAll(arr);
                for (int i = 0; i < arr.size(); i++) {
                    pretzelMoveList.add(pretzelNum);
                }
                break;
            case ">=":
                arr = numbersGreaterThan(peanutNum, numPeanuts);
                peanutMoveList.addAll(arr);
                for (int i = 0; i < arr.size(); i++) {
                    pretzelMoveList.add(pretzelNum);
                }
                break;
            case "=<":
                arr = numbersLessThan(pretzelNum);
                for (int i = 0; i < arr.size(); i++) {
                    peanutMoveList.add(peanutNum);
                }
                pretzelMoveList.addAll(arr);
                break;
            case "=>":
                arr = numbersGreaterThan(pretzelNum, numPretzels);
                for (int i = 0; i < arr.size(); i++) {
                    peanutMoveList.add(peanutNum);
                }
                pretzelMoveList.addAll(arr);
                break;
            case "<>":
                arr = numbersLessThan(peanutNum);
                arr2 = numbersGreaterThan(pretzelNum, numPretzels);
                for (Integer i : arr) {
                    for (Integer j : arr2) {
                        peanutMoveList.add(i);
                        pretzelMoveList.add(j);
                    }
                }
                break;
            case "<<":
                arr = numbersLessThan(peanutNum);
                arr2 = numbersLessThan(pretzelNum);
                for (Integer i : arr) {
                    for (Integer j : arr2) {
                        peanutMoveList.add(i);
                        pretzelMoveList.add(j);
                    }
                }
                break;
            case ">>":
                arr = numbersGreaterThan(peanutNum, numPeanuts);
                arr2 = numbersGreaterThan(pretzelNum, numPretzels);
                for (Integer i : arr) {
                    for (Integer j : arr2) {
                        peanutMoveList.add(i);
                        pretzelMoveList.add(j);
                    }
                }
                break;
            case "><":
                arr = numbersGreaterThan(peanutNum, numPeanuts);
                arr2 = numbersLessThan(pretzelNum);
                for (Integer i : arr) {
                    for (Integer j : arr2) {
                        peanutMoveList.add(i);
                        pretzelMoveList.add(j);
                    }
                }
                break;
        }

    }

    private static ArrayList<Integer> numbersLessThan(int num) {
        final ArrayList<Integer> arr = new ArrayList<>();
        for (int i = num - 1; i >= 0; i--) {
            arr.add(i);
        }
        return arr;
    }

    private static ArrayList<Integer> numbersGreaterThan(int num, int max) {
        final ArrayList<Integer> arr = new ArrayList<>();
        for (int i = num + 1; i < max; i++) {
            arr.add(i);
        }

        return arr;
    }
}
