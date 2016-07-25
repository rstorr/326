public class RollinGroup extends Rollin {

	@Override
	public int decideRoll(int roll) {
		int[] countOfFaces = new int[diceInGame];
		int posToThrow = 0;
		int groupsOfTwo = 0;

		countOfFaces[roll-1]++;

		for (int i = 0; i < dice.length; i++){
			countOfFaces[dice[i]-1]++;
		}

		outer:
		for(int i = 0; i < countOfFaces.length; i++){
			if (countOfFaces[i] < 2) {
				for (int j = 0; j < dice.length; j++){
					if (i+1 == dice[j]){
						posToThrow = j;
						break outer;
					}
				}
			} else {
				groupsOfTwo++;
				if (groupsOfTwo == 3){
					return rng.nextInt(diceInGame);
				}
			}
		}

		return posToThrow;
	}

	public static void main(String[] args) {
		int scenarios = 1000;
		int turnsTotal = 0;
		int i = 0;
		while (i < scenarios) {
			Rollin test = new RollinGroup();
			int turns = 0;
			while (!test.isComplete()) {
				test.turn();
				turns++;
			}
			System.out.println(turns);
			i++;
			turnsTotal += turns;
		}
}
}
