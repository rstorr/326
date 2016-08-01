import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by reuben on 27/07/16.
 */
public class Arithmetic {
    private static ArrayList<Character> correctOperations = new ArrayList<>();

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Character> operations = new ArrayList<>();
        boolean firstLine = true;

        char[] possOperations = {'+', '*'};

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
                findFormula(numbers.size() - 1, possOperations, order,
                        numbers, operations, Integer.parseInt(lineArr[0]));
                if (correctOperations.isEmpty()){
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

    private static int calculateL(int sum, ArrayList<Integer> numbers, ArrayList<Character> operations) {

        if (numbers.size() > 1) {
            if (operations.get(0) == '+') {
                sum += numbers.get(0) + numbers.get(1);
            } else {
                sum += numbers.get(0) * numbers.get(1);
            }
            numbers.remove(0);
            numbers.remove(0);
            operations.remove(0);
        } else if (numbers.size() == 1) {
            if (operations.get(0) == '+') {
                sum += numbers.get(0);
            } else {
                sum *= numbers.get(0);
            }
            numbers.remove(0);
            operations.remove(0);

        } else {
            return sum;
        }

        return calculateL(sum, numbers, operations);

    }

    private static int calculateN(ArrayList<Integer> numbers, ArrayList<Character> operations) {
        int sum = 0;

        //calculate all the multiplication.
       for (int i = 0; i < operations.size(); i++){
           if (operations.get(i) == '*') {
               int multiplySum = numbers.get(i) * numbers.get(i + 1);
               numbers.remove(i+1);
               numbers.remove(i);
               numbers.add(i, multiplySum);
               operations.remove(i);
           }
       }

       for (Integer n : numbers){
           sum += n;
       }

       return sum;
    }

    private static void findFormula(int maxLen, char[] possOps, char order, ArrayList<Integer> numbers, ArrayList<Character> operations,
                                    int target) {

        if (operations.size() == maxLen){
            ArrayList<Integer> nums = new ArrayList<>();
            nums.addAll(numbers);
            ArrayList<Character> ops = new ArrayList<>();
            ops.addAll(operations);

            if (order == 'N') {
                if (calculateN(nums, ops) == target) {
                    correctOperations = operations;
                }
            } else if (order == 'L') {
                if (calculateL(0, nums, ops) == target) {
                    correctOperations = operations;
                }
            }
        } else {
            for (int i = 0; i < possOps.length; i++) {
                ArrayList<Character> oldOps = new ArrayList<>();
                oldOps.addAll(operations);
                ArrayList<Character> newOps = new ArrayList<>();
                newOps.addAll(operations);
                newOps.add(possOps[i]);
                findFormula(maxLen, possOps, order, numbers,
                        newOps, target);
                operations = oldOps;
            }
        }
    }
}
