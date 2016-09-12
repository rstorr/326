import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Harmonious Numbers prints out all amicable in their pairs up to where the
 * smallest number of the greatest pair is less than two million.
 *
 * @author Reuben Storr
 */

public class Harmonious_Numbers {
    private final static LinkedHashMap<Integer, Integer> pairs =
            new LinkedHashMap<Integer, Integer>();

    public static void main(String args[]){
        for (int i = 2; i < 2000000 ; i++){
            final int sumOne = sumOfProperDiv(i);
            final int sumTwo = sumOfProperDiv(sumOne);

            if (i == sumTwo && i != sumOne){
                if (sumOne < sumTwo){
                    if (!pairs.containsKey(sumOne)) {
                        pairs.put(sumOne, sumTwo);
                    }
                } else {
                    if (!pairs.containsKey(sumTwo)) {
                        pairs.put(sumTwo, sumOne);
                    }
                }
            }
        }

        for (Map.Entry<Integer,Integer> entry :
                pairs.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    private static int sumOfProperDiv(final int input){
        final int maxDiviser = (int) Math.sqrt(input);
        int sum = 0;
        for(int i = 2; i <= maxDiviser; i++) {
            if(input % i == 0) {
                final int correspondingD = input/i;
                sum += i;
                if(correspondingD != i) {
                    sum += correspondingD;
                }
            }
        }
        return sum;
    }
}