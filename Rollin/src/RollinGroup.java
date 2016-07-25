public class RollinGroup extends Rollin {

	@Override
	public int decideRoll(int roll) {
		/*
		 * Can work out probability of next roll based on previous rolls.
		 * bc's test edit
		 */

		     int[] tmp = new int[1];
		     int[] first_array = new int[6];
		     int[] second_array = new int[6];

		     roll = first_array[1];
		     for(r)
		     System.out.println(first_array[1]);

		return rng.nextInt(diceInGame + 2);
	}

	public static void main(String[] args) {

		Rollin test = new RollinGroup();
		int turns = 0;
		while (!test.isComplete()) {
			test.turn();
			turns++;
		}
		System.out.println(turns);
	}

}
