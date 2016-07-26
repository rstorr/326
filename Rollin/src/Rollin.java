import java.util.*;

/**
 * An abstract class for the Rollin' etude used in COCSC326 S2 2016.
 *
 * @author Michael Albert
 */
public abstract class Rollin {

    protected static final int facesPerDie = 6;
    protected static final int diceInGame = 6;

    /* A convenience array used in determining whether or not six dice
       form two sets -- it represents all the possible partitions of the
       indices 0 to diceInGame-1 inclusive into two groups of three.
    */
    protected static final int[][][] setIndices = new int[][][]{
        {{0, 1, 2}, {3, 4, 5}},
        {{0, 1, 3}, {2, 4, 5}},
        {{0, 1, 4}, {2, 3, 5}},
        {{0, 1, 5}, {2, 3, 4}},
        {{0, 2, 3}, {1, 4, 5}},
        {{0, 2, 4}, {1, 3, 5}},
        {{0, 2, 5}, {1, 3, 4}},
        {{0, 3, 4}, {1, 2, 5}},
        {{0, 3, 5}, {1, 2, 4}},
        {{0, 4, 5}, {1, 2, 3}}
    };

    /*  The array of dice must contain exactly 6 dice.
    */
    protected final int[] dice = new int[diceInGame];

    /** The random number generator state.
        This class uses it only for generating random dice throws;
        subclasses may use it to help decide what to do.
    */
    protected final Random rng = new Random();

    /** Simulate rolling a die.
        @return a randomly selected integer between 1 and 6 inclusive,
        with all values (approximately) equally likely.
    */
    public final int randomDie() {
        return rng.nextInt(facesPerDie)+1;
    }

    /** The problem specification says that "You have six dice,
        initially random."  This constructor makes it so.
    */
    public Rollin() {
        for (int i = 0; i < diceInGame; i++) dice[i] = randomDie();
    }

    /**
     * Obvious accessor method. Can be used by the 'supervisor' program to
     * ensure that you are maintaining your dice values correctly.  It is
     * would be considered bad style to do 'return dice' here, because it
     * is dangerous to return a mutable part of an object, since the
     * caller could then change that part's state (and so this object's
     * state) without this object knowing about it.
     * @return A copy of the array of dice values.
     */
    public final int[] getDice() {
        return java.util.Arrays.copyOf(dice, dice.length);
    }

    /**
     * This is the method you need to implement.  It should decide what
     * to do with a given roll and the current dice.  If using the roll
     * to substitute for an existing die, it should return the index of
     * the die that is being replaced, i.e., if the roll is replacing the
     * value dice[2], then it should return 2.  If the roll is not being
     * used, then it may return any value outside the range from 0 to
     * 5 inclusive.

     * Note that while your class may have other methods, none of them
     * should modify the array dice, e.g., by sorting it, since the
     * supervisor program will expect the results of handleRoll to be
     * applied to the original set of dice (and subsequently to its
     * modified versions if replacements have been made).

     * @param roll
     * The value of the die roll
     * @return The index of the die whose value will be replaced by the
     * roll, or any integer outside 0 to 5 if no replacement is made.
     */
    public abstract int decideRoll(int roll);

    /** This method throws a new die, passes the result to your
        decideRoll(_) method, and acts on your decision.  This
        should be the only place other than the constructors where
        dice[] is changed.
    */
    public final void turn() {
        final int roll = randomDie();
        final int choice = decideRoll(roll);
        if (0 <= choice && choice < diceInGame) dice[choice] = roll;
    }

    /**
     * Determine whether the current dice form two sets.
     * @return true if the dice form two sets, false otherwise
     */
    public final boolean isComplete() {
        for (int[][] si : setIndices) {
            if (isSet(si[0]) && isSet(si[1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the dice at a given triple of indices form a set.
     * 
     * @param indices the indices
     * @return true if the dice at those indices form a set, false otherwise.
     */
    protected boolean isSet(int[] indices) {
        // First just get the values at those indices to save typing.
        final int a = dice[indices[0]];
        final int b = dice[indices[1]];
        final int c = dice[indices[2]];
        // All three dice the same is a set
        if (a == b && b == c) {
            return true;
        }
        // If not all three are the same, then any two the same is not a set
        if (a == b || a == c || b == c) {
            return false;
        }
        
        // If all three are different and largest minus smallest is 2 then it
        // is a set, otherwise not.
        final int max = Math.max(a, Math.max(b, c));
        final int min = Math.min(a, Math.min(b, c));
        return max - min == 2;
    }

    /*  You will write a subclass like this:
        class MyRollin extends Rollin {
            public int decideRoll(int roll) {
		return rng.nextInt(diceInGame+2);
            }
           
            public static void main(String[] args) {
		Rollin test = new MyRollin();
		int turns = 0;
		while (!test.isComplete()) {
		    test.turn();
		    turns++;
		}
		System.out.println(turns);
	    }
        }
        In 1000 runs, the statistics for the number of turns were
        minimum        :   0
        first quartile :   6
        median         :  15
        mean           :  20.5
        third quartile :  29
        maximum        : 126

        The problem specification says NOT to submit a program that
        just hopes to get lucky like this one does; your program
        should do better.
    */
}


