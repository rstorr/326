import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by reuben on 27/07/16.
 */
public class Arithmetic {
    private static ArrayList<Character> correctOperations = new ArrayList<>();
    private static final char[] possOps = {'+', '*'};

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Character> operations = new ArrayList<>();
        boolean firstLine = true;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] lineArr = line.split(" ");
            if (firstLine) {
                numbers.clear();
                correctOperations.clear();
                for (String s : lineArr){
                    numbers.add(Integer.parseInt(s));
                }
                firstLine = false;
            } else {
                char order = lineArr[1].charAt(0);
                int target = Integer.parseInt(lineArr[0]);
                findFormula(order, numbers, operations, target);
                if (correctOperations.isEmpty() && numbers.get(0) != target){
                    System.out.println(order + " impossible");
                } else {
                    System.out.print(order + " ");
                    for (int i = 0; i < numbers.size(); i++){
                        if (correctOperations.size() > i){
                            System.out.print(numbers.get(i) + " " + correctOperations.get(i) + " ");
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

    private static int calculateL(boolean firstRun, int sum, ArrayList<Integer> numbers, ArrayList<Character> operations) {

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

    private static int calculateN(final ArrayList<Integer> numbers, final ArrayList<Character> operations) {
        int sum = 0;

        ArrayList<Integer> nums = new ArrayList<>(numbers);
        ArrayList<Character> ops = new ArrayList<>(operations);

        //calculate all the multiplication.
        for (int i = 0; i < ops.size(); i++){
            if (ops.get(i) == '*') {
                int multiplySum = nums.get(i) * nums.get(i + 1);
                nums.remove(i);
                nums.remove(i);
                nums.add(i, multiplySum);
                ops.remove(i);
                i -= 1;
            }
        }

        for (Integer n : nums){
            sum += n;
        }

        return sum;
    }

    private static void findFormula(final char order, final ArrayList<Integer> numbers,
                                    ArrayList<Character> operations, final int target) {

        if (operations.size() == numbers.size() - 1){
            ArrayList<Integer> nums = new ArrayList<>(numbers);
            ArrayList<Character> ops = new ArrayList<>(operations);
            if (order == 'N') {
                if (calculateN(nums, ops) == target) {
                    correctOperations = operations;
                }
            } else if (order == 'L') {
                if (calculateL(true, 0, nums, ops) == target) {
                    correctOperations = operations;
                }
            }
        } else {
            for (char op: possOps) {
                ArrayList<Character> oldOps = new ArrayList<>(operations);
                ArrayList<Character> newOps = new ArrayList<>(operations);
                newOps.add(op);
                findFormula(order, numbers,
                        newOps, target);
                operations = oldOps;
            }
        }
    }
}
