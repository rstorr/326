/**
 * RollinGroup forms two sets of three dice as quickly as possible where
 * a set consists either of three diceshowing the same number, e.g. 444, or
 * three dice in sequence, e.g. 123 or 345.
 *
 * @author Reuben Storr, Bayley Millar, Joe Benn, George Bonnici-carter,
 * Sebastian Buddle, Blake Carter
 */

public class RollinGroup extends Rollin {

	public static void main(String[] args) {
		final Rollin test = new RollinGroup();
		int turns = 0;
		while (!test.isComplete()) {
			test.turn();
			turns++;
		}
		System.out.println(turns);
	}

	@Override
	public int decideRoll(int roll) {
		final int[] countOfFaces = new int[diceInGame];
		int groupsOfTwo = 0;

		countOfFaces[roll-1]++;

		for (int i = 0; i < dice.length; i++){
			countOfFaces[dice[i]-1]++;
		}

		for(int i = 0; i < countOfFaces.length; i++){
			if (countOfFaces[i] < 2) {
				for (int j = 0; j < dice.length; j++){
					if (i+1 == dice[j]){
						return j;
					}
				}
			}
		}

		return rng.nextInt(diceInGame);
	}
}
