/**
 * DNA_Line stores a line of DNA from stdin as an object.
 * @author Reuben Storr, Bayley millar
 *
 */
public class DNA_Line {
	private char state;
	private char[] compassDirs;
	private char[] statesAfterAnt;
	
	/**
	 * This method collects the DNA line
	 * @param state: is the inputed state of type char
	 * @param compassDirs: is the sequence of the compass directions
	 * @param statesAfterAnt: sequence representing the state
	 * of the point after the ant leaves
	 */
	public DNA_Line(char state, char[] compassDirs, char[] statesAfterAnt) {
		this.state = state;
		this.compassDirs = compassDirs;
		this.statesAfterAnt = statesAfterAnt;
	}
	
	/** 
	 *getter: gets the current state
	 * @return the state
	 */
	public char getState() {
		return state;
	}
	
	/**
	 *  getter: gets the compass of type char[]
	 * @return returns the compass
	 */
	public char[] getCompassDirs() {
		return compassDirs;
	}
	
	/**
	 * getter: gets the states after the ant of type char[]
	 * @return returns the states after the Ant
	 */
	public char[] getStatesAfterAnt() {
		return statesAfterAnt;
	}

}
