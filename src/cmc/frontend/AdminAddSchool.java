package cmc.frontend;

import java.util.Scanner;

/**
 * TUI to take information from an admin to add a school.
 * @author Roman Lefler
 * @version Mar 11, 2025
 */
public class AdminAddSchool {
	
	/**
	 * Tries to get the next line of a scanner, but returns {@code null}
	 * if EOF is found.
	 */
	private static String tryLine(Scanner s) {
		
		return s.hasNextLine() ? s.nextLine() : null;
	}
	
	/**
	 * Parses an int.
	 * @param s The string to parse.
	 * @return Parses an int or returns -1 if invalid.
	 */
	private static int tryParseInt(String s) {
		try {
			return Integer.parseInt(s);
		}
		catch(NumberFormatException e)
		{
			return -1;
		}
	}
	
	/**
	 * Prompts the user for school information with std I/O and returns
	 * a created university.
	 * Note that this method does NOT interact with the DB at all.
	 * @param s A scanner to read input from.
	 * @return A university or {@code null} if the user aborts.
	 * @author Roman Lefler
	 * @version Mar 11, 2025
	 */
	// TODO: This method should return a University object.
	public static String[] prompt(Scanner s) {
		
		System.out.println("Fill out the university's information or");
		// Ctrl + D sends EOF, i.e. no next line will be in the scanner
		System.out.println("press Ctrl+D to cancel.\n");
		
		System.out.print("Name: ");
		String name = tryLine(s);
		if(name == null) return null;
		name = name.toUpperCase();
		
		System.out.println("Put a state or 'FOREIGN'");
		System.out.print("State: ");
		String state = tryLine(s);
		if(state == null) return null;
		state = state.toUpperCase();
		
		
		String location = null;
		// This loop will only repeat if it is given invalid input
		do {
			String[] friendly = new String[] { "Urban", "Suburban", "Small-City", "Unknown" };
			String[] options = new String[] { "URBAN", "SUBURBAN", "SMALL-CITY", "-1" };
			
			int chosen = listPrompt("Location: ", friendly, s);
			if(chosen == -2) return null;
			else if(chosen == -1) System.out.println("Pick a number 1-4!");
			else location = options[chosen];
		}
		while(location == null);
		
		String control = null;
		do {
			String[] friendly = new String[] { "State", "City", "Private", "Unknown" };
			String[] options = new String[] { "STATE", "CITY", "PRIVATE", "-1" };
			
			int chosen = listPrompt("Location: ", friendly, s);
			if(chosen == -2) return null;
			else if(chosen == -1) System.out.println("Pick a number 1-4!");
			else control = options[chosen];
		}
		while(control == null);
		
		System.out.println("Just press enter for any of the following if unknown.");
		NumberItem[] numQs = new NumberItem[] {
			new NumberItem("# of Students: ", 0, Integer.MAX_VALUE),
			new NumberItem("% Female: ", 0, 100),
			new NumberItem("Verbal SAT Score: ", 200, 800),
			new NumberItem("Math SAT Score: ", 200, 800),
			new NumberItem("Expenses", 0, Integer.MAX_VALUE),
			new NumberItem("% Financial Aid: ", 0, 100),
			new NumberItem("# of Applicants:", 0, Integer.MAX_VALUE),
			new NumberItem("% Admitted: ", 0, 100),
			new NumberItem("% Enrolled: ", 0, 100),
			new NumberItem("Academics Scale: ", 0, 5),
			new NumberItem("Social Scale: ", 0, 5),
			new NumberItem("Quality of Life Scale: ", 0, 5)
		};
		String[] numAns = queryAll(numQs, s);
		
		// TODO: Return a University object
		String[] uni = new String[16];
		uni[0] = name;
		uni[1] = state;
		uni[2] = location;
		uni[3] = control;
		System.arraycopy(numAns, 0, uni, 4, 12);
		
		return uni;
	}
	
	/**
	 * Information to use with {@link AdminAddSchool#queryAll}.
	 */
	static class NumberItem {
		public NumberItem(String q, int lo, int hi) {
			question = q;
			min = lo;
			max = hi;
		}
		
		protected String question;
		protected int min;
		protected int max;
	}
	
	/**
	 * Asks all questions and returns an array with their answers
	 * as numbers in an array corresponding to the inputed array.
	 */
	private static String[] queryAll(NumberItem[] items, Scanner s) {
		int sz = items.length;
		String[] output = new String[sz];
		
		for(int i = 0; i < sz; i++) {
			NumberItem k = items[i];
			System.out.println(k.question);
			
			String input = tryLine(s);
			if(input == null) return null;
			output[i] = numInRange(input, k.min, k.max) + "";
		}
		
		return output;
	}
	
	/**
	 * Returns the number from a string if it's in range or
	 * -1 if out of range or invalid.
	 * @param s Input string
	 * @param lo Minimum inclusive
	 * @param hi Maximum inclusive
	 * @return Number in range or -1.
	 */
	private static int numInRange(String s, int lo, int hi) {
		int num = tryParseInt(s);
		if(num < lo || num > hi) return -1;
		else return num;
	}
	
	/**
	 * Prompts the user to pick an item of a list by number.
	 * The user's options will start at 1 but the return value
	 * is zero-indexed.
	 * @param question The question to print (e.g. "Location: ")
	 * @param friendlyOptions User-friendly names to put by the numbers
	 * @param sc Scanner to query
	 * @return The picked item's index, -1 if invalid, or -2 if canceled (received
	 *         EOF).
	 */
	private static int listPrompt(String question, String[] friendlyOptions, Scanner sc) {
		
		int sz = friendlyOptions.length;
		
		for(int i = 0; i < sz; i++) {
			String msg = String.format("%d) %s", i + 1, friendlyOptions[i]);
			System.out.println(msg);
		}
		
		System.out.print("\n" + question);
		
		String given = tryLine(sc);
		if(given == null) return -2;
		
		int num = tryParseInt(given);
		if(num <= 0 || num > sz) return -1;
		else return num - 1;
		
	}
	
}
