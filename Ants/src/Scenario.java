import java.util.ArrayList;

public class Scenario {
	private final int planeSize = 1000;
	private char[][] plane = new char[planeSize][planeSize];
	private int antXCoord = planeSize/2;
	private int antYCoord = planeSize/2;
	private int mathAntY = antYCoord - planeSize/2;
	private ArrayList<DNA_Line> DNA = new ArrayList<DNA_Line>();
	private int totalSteps;

	public void setTotalSteps(int totalSteps) {
		this.totalSteps = totalSteps;
	}

	public char setupPlane() {
		char defState = DNA.get(0).getState();
		for (char[] arr : plane) {
			for (char state : arr) {
				state = defState;
			}
		}
		return defState;
	}

	public void playScenario() {
		char curState = setupPlane();
		char cameFrom = 'S';
		int steps = 0;
		while (steps < getTotalSteps()) {
			for (DNA_Line line : DNA) {
				if (line.getState() == curState) {
					char[] statesArr = line.getStatesAfterAnt();
					char[] directionsArr = line.getCompassDirs();
					switch (cameFrom) {
					case 'N':
						changeCurCoordState(statesArr[0]);
						moveAnt(directionsArr[0]);
						cameFrom = directionsArr[0];
						break;
					case 'E':
						changeCurCoordState(statesArr[1]);
						moveAnt(directionsArr[1]);
						cameFrom = directionsArr[1];
						break;
					case 'S':
						changeCurCoordState(statesArr[2]);
						moveAnt(directionsArr[2]);
						cameFrom = directionsArr[2];
						break;
					case 'W':
						changeCurCoordState(statesArr[3]);
						moveAnt(directionsArr[3]);
						cameFrom = directionsArr[3];
						break;
					}
				}
			}
			steps++;
		}

		printScenario();
	}

	public void changeCurCoordState(char newState) {
		plane[antXCoord][antYCoord] = newState;
		System.out.println("cur coord: " + (antXCoord - planeSize/2) + " " + (antYCoord - planeSize/2));
	}

	public int getTotalSteps() {
		return totalSteps;
	}

	public void addDnaLine(String line) {
		String[] splitLine = line.split("\\s+");
		char state = splitLine[0].charAt(0);
		char[] compassDirs = splitLine[1].toCharArray();
		char[] statesAfterAnt = splitLine[2].toCharArray();

		DNA.add(new DNA_Line(state, compassDirs, statesAfterAnt));
	}

	private void moveAnt(char dir) {
		switch (dir) {
		case 'N':
			antYCoord += 1;
			break;
		case 'E':
			antXCoord += 1;
			break;
		case 'S':
			antYCoord -= 1;
			break;
		case 'W':
			antXCoord -= 1;
			break;
		}
	}

	private void printScenario() {
		for (DNA_Line line : DNA) {
			System.out.print(line.getState() + " ");
			for (char dir : line.getCompassDirs()) {
				System.out.print(dir);
			}
			System.out.print(" ");
			for (char state : line.getStatesAfterAnt()) {
				System.out.print(state);
			}
			System.out.println();
		}
		System.out.println(totalSteps);
		System.out.println("# " + (antXCoord - planeSize/2) + " " + (antYCoord - planeSize/2));
	}

}
