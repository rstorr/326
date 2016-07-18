/**
 * DNA_Line stores a line of DNA from stdin as an object.
 * @author reuben
 *
 */
public class DNA_Line {
	private char state;
	private char[] compassDirs;
	private char[] statesAfterAnt;

	public DNA_Line(char state, char[] compassDirs, char[] statesAfterAnt) {
		this.state = state;
		this.compassDirs = compassDirs;
		this.statesAfterAnt = statesAfterAnt;
	}

	public char getState() {
		return state;
	}

	public char[] getCompassDirs() {
		return compassDirs;
	}

	public char[] getStatesAfterAnt() {
		return statesAfterAnt;
	}

}
