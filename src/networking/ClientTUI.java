package networking;
import Exceptions.*;
import java.io.PrintWriter;
import java.util.Scanner;
import networking.Commands;

/**
 * 
 * 
 * the class ClientTUI represent the Textual User Interface for the Client of Abalone
 */
public class ClientTUI {
		private PrintWriter writer; 
		private Client client; 
		/**
		 * constructor for the class ClientTUI
		 * @param Client
		 */
		public ClientTUI(Client Client) {
			this.client = client;
			this.writer = new PrintWriter(System.out, true);
		}
		
		/**
		 * a simple start method for starting the Textual User Interface(TUI)
		 * @throws ServerUnavailableException in case the server is down
		 */
		public void start() throws ServerUnavailableException{ 
			String input = " ";
			while (!(input.equals(Commands.DISCONNECT_LOBBY))) {
				Scanner scanner = new Scanner(System.in);
				input = scanner.nextLine();
				handleUserInput(input);
			}
			this.client.disconnectFromLobby();
		}
		/**
		 * @requires input that is not null
		 * This class handles the user input 
		 * com stands for command and the appendix means the rest of the input excluding the command 
		 * @param input - the user input(console)
		 * @throws ServerUnavailableException in case the server is down
		 */
		public void handleUserInput(String input) throws ServerUnavailableException{
			String[] splitting = input.split(" ");
			String com = splitting[0];
			String[] appendice = new String[2];
			if(splitting.length == 3) {
				appendice[0] = splitting[1];
				appendice[1] = splitting[2];
			}
			switch(com) {
			case Commands.MAKE_MOVE:
				this.client.sendMove(appendice);
			break;
			case Commands.JOIN: 
				this.client.startGame();
				break;
			case Commands.DISCONNECT_LOBBY:
				this.client.disconnectFromLobby();
				break;
			default:
				System.out.println("The command is not valid");
				printHelpMenu();
			}
			
		}
		/**
		 * this class is just a help menu 
		 */
		public void printHelpMenu() {
	        System.out.println("join <name> <numberofplayersgame>.......to join in a lobby"
	                + System.lineSeparator() + "start.......to indicate that you are ready to star a game"
	                + System.lineSeparator() + "move <marbles> <direction>....... to move marbles in a direction"
	                + System.lineSeparator() + "disconnect......to disconnect a server");
		}
		/**
		 * the purpose of this class is to display - print a message to the console
		 * @param msg the message that is going to be printed
		 */
		public void displayMessage(String msg) {
	        this.writer.println(msg);
	    }
}
