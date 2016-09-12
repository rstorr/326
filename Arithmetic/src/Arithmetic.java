import java.util.ArrayList;
import java.util.Scanner;

/**
 * Arithmetic uses addition and multiplication to reach a target value using
 * a set of fixed integers in either proper order i.e 1 + 2 * 3 = 7 or left-to-right
 * i.e 1 + 2 * 3 = 9.
 *
 * @author Reuben Storr
 */
public class Arithmetic {
    private static final char[] possibleOperations = {'+', '*'};
    private static ArrayList<Character> correctOps = new ArrayList<>();

    public static void main(String args[]) {
        final Scanner sc = new Scanner(System.in);
        final ArrayList<Integer> numbers = new ArrayList<>();
        boolean firstLine = true;

        while (sc.hasNextLine()) {
            final String line = sc.nextLine();
            final String[] lineArr = line.split(" ");
            if (firstLine) {
                firstLine = false;
                numbers.clear();
                correctOps.clear();

                for (String s : lineArr){
                    numbers.add(Integer.parseInt(s));
                }

            } else {
                final char order = lineArr[1].charAt(0);
                final int target = Integer.parseInt(lineArr[0]);
                findFormula(order, numbers, new ArrayList<>(), target);

                if (correctOps.size() == 0 && numbers.get(0) != target){
                    System.out.println(order + " impossible");
                } else {
                    System.out.print(order + " ");
                    for (int i = 0; i < numbers.size(); i++){
                        if (correctOps.size() > i){
                            System.out.print(numbers.get(i) + " " +
                                    correctOps.get(i) + " ");
                        } else {
                            System.out.print(numbers.get(i));
                        }
                    }
                    System.out.println();
                }
                firstLine = true;
            }
        }
    }

    private static int calculateL(final boolean firstRun, int sum,
                                  final ArrayList<Integer> numbers,
                                  final ArrayList<Character> operations) {
        if (firstRun){
            sum = numbers.get(0);
            numbers.remove(0);
        }

        if (numbers.size() > 0) {
            if (operations.get(0) == '+') {
                sum += numbers.get(0);
            } else if (operations.get(0) == '*'){
                sum *= numbers.get(0);
            }
            numbers.remove(0);
            operations.remove(0);
        } else {
            return sum;
        }

        return calculateL(false, sum, numbers, operations);

    }

    private static int calculateN(final ArrayList<Integer> numbers,
                                  final ArrayList<Character> operations) {
        int sum = 0;

        for (int i = 0; i < operations.size(); i++){
            if (operations.get(i) == '*') {
                int multiplySum = numbers.get(i) * numbers.get(i + 1);
                numbers.remove(i);
                numbers.remove(i);
                numbers.add(i, multiplySum);
                operations.remove(i);
                i -= 1;
            }
        }

        for (Integer n : numbers){
            sum += n;
        }

        return sum;
    }

    private static void findFormula(final char order, final ArrayList<Integer> numbers,
                                    ArrayList<Character> operations, final int target) {
        if (operations.size() == numbers.size() - 1){
            final ArrayList<Integer> nums = new ArrayList<>(numbers);
            final ArrayList<Character> ops = new ArrayList<>(operations);
            if (order == 'N') {
                if (calculateN(nums, ops) == target) {
                    correctOps = operations;
                }
            } else if (order == 'L') {
                if (calculateL(true, 0, nums, ops) == target) {
                    correctOps = operations;
                }
            }
        } else {
            for (char op: possibleOperations) {
                final ArrayList<Character> oldOps = new ArrayList<>(operations);
                final ArrayList<Character> newOps = new ArrayList<>(operations);
                newOps.add(op);
                findFormula(order, numbers, newOps, target);
                operations = oldOps;
            }
        }
    }
}
