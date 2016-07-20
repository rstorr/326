import java.util.Scanner;

/**
 * Ants class holds the main method. Takes the DNA and total steps input from stdin,
 * stores it in a Scenario object then executes the scenario.
 * @author reuben
 *
 */
public class Ants {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter scenarios:");
		try{
		while (sc.hasNextLine()) {
			Scenario scenario = new Scenario();
			String line = nextLine(sc);
			while (!line.equals("") || line != null) {
				try {
					scenario.setTotalSteps(Integer.parseInt(line));
					scenario.playScenario();
					break;

				} catch (NumberFormatException e) {
					scenario.addDnaLine(line);
					line = nextLine(sc);
				}
			}
		}
		}catch(StringIndexOutOfBoundsException e){
			System.out.println("please restart and enter valid input");
		}
	}

	
	
	/**
	 * Returns next valid line, ignoring comment lines denoted
	 * with a '#'.
	 * @param sc Scanner to get input from stdin.
	 * @return Returns valid line.
	 */
	private static String nextLine(Scanner sc) {
		String line = sc.nextLine();
		
		while (line.substring(0, 1).equals('#') && sc.hasNextLine()) {
			line = sc.nextLine();
		}
		
		if (line.substring(0, 1).equals('#')) {
			return null;
		} else {
			return line;
		}
	}
}
