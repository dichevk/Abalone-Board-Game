package networking;

import java.io.PrintWriter;
import java.util.Scanner;

public class ServerTUI {
	private PrintWriter console;
	public ServerTUI() {
		console = new PrintWriter(System.out, true);
	}
	/**
	 * Writes the given message to standard output.
	 * 
	 * @param message the message to write to the standard output.
	 */
	public void showMessage(String message) {
		console.println(message);
	}
	/**
	 * Prints the question and asks the user to input a String.
	 * 
	 * @param question The question to show to the user
	 * @return The user input as a String
	 */
	public String getString(String question) {
		// To be implemented
		Scanner reader = new Scanner(System.in);
		console.println(question);
		String b = reader.nextLine();
		return b;
	}
	/**
	 * Prints the question and asks the user to input an Integer.
	 * 
	 * @param question The question to show to the user
	 * @return The written Integer.
	 */
	public int getInt(String question) {
		// To be implemented
		Scanner reader = new Scanner(System.in);
		console.println(question);
		int b = Integer.parseInt(reader.nextLine());
		return b;
	}
	/**
	 * Prints the question and asks the user for a yes/no answer.
	 * 
	 * @param question The question to show to the user
	 * @return The user input as boolean.
	 */
	public boolean getBoolean(String question) {
		// To be implemented
		Scanner reader = new Scanner(System.in);
		console.println(question);
		boolean b = Boolean.getBoolean(reader.nextLine());
		return b;
	}
}
