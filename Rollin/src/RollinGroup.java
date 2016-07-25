public class RollinGroup extends Rollin {

	@Override
	public int decideRoll(int roll) {
		int[] countOfFaces = new int[diceInGame];
		int[] posToKeep = new int[diceInGame];
				
		countOfFaces[roll-1]++;
		
		for (int i = 0; i < dice.length; i++){
			countOfFaces[dice[i]-1]++;
		}
		
		
		for(int i = 0; i < countOfFaces.length; i++){
			if (countOfFaces[i] == 2) {
				for (int j = 0; j < dice.length; j++){
					if (dice[j]-1 == i){
						posToKeep[j] = 1;
					}
				}
			}
		}

		
		for(int i = 0; i < posToKeep.length; i++){
			if (posToKeep[i] == 0){
				System.out.println("swap at pos " + i);
				return i;
			}
		}
		
		return 10;

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
