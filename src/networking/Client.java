package networking;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import Exceptions.*;
import Exceptions.ProtocolException;
/**
 * 
 * 
 * this class represents the client for the applet
 */
public class Client implements Runnable  {
	private Socket socket; 
	private BufferedReader input; 
	private BufferedWriter output; 
	private ClientTUI clientView; 
	
	/**
	 * a constructor for the class Client 
	 */
		public Client() {
			this.clientView = new ClientTUI(this); //initialize the TUI
			
				}
		
		/**
		 * starts a new Client by doing a handshake
		 * the method catches the predictable exceptions 
		 */
		
		public void startClient()  {
			try {
				this.createConnection(); //implemented below
				this.handleHello();  //implemented below
				
			}
			
			catch(ExitProgramException e) {
				closeConnection(); //implemented below
			}
		}
		
		/**
		 * this method creates a connection to the server by requesting the IP and the port
		 * @throws ExitProgramException if the connection fails 
		 */
		
		public void createConnection() throws ExitProgramException{
			Reset();
			while(socket == null) {
				String host = "127.0.0.1";
				int port = 4999; 
				try { 
					InetAddress address = InetAddress.getByName(host);
					System.out.println("Connecting to " + address + ":" + port + "...");
					socket = new Socket(address, port);
					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					
					
				} catch (IOException e) {
					System.out.println("Could not create socket on" + host + "on the port: " + port);
				}
			}
		}
		
		/**
		 * a method that handles the handshake
		 * @throws ServerUnavailableException  
		 * @throws ProtocolException if the response from the server is not valid
		 */
	/*	public void handleInitialization() throws ServerUnavailableException, ProtocolException {
			this.sendMesssage(Commands.INITIALIZE);
			String response = this.readLineFromServer().split(Commands.DELIMITER);
			if(response[0].equalsIgnoreCase(Commands.INITIALIZE)) {
				this.clientView.displayMessage;//comes from the TUI
				
			}
			throw new ProtocolException("Initialization not successful " + response);
			
		}
		*/
		/**
		 * a synchronized method used to send a message to the connected server
		 * @param msg
		 * @throws ServerUnavailableException
		 */
		public synchronized void sendMesssage(String msg) throws ServerUnavailableException{
			if (output !=null) {
				try { 
					output.write(msg);
					output.newLine();
					output.flush();
				} catch(IOException e) {
					System.out.println(e.getMessage());
					throw new ServerUnavailableException("Could not write" + "to server.");
				}
			}
		}
		
		public void handleHello() {
			try {
				this.sendMesssage(Commands.INITIALIZE);
				String answer = this.readLineFromServer();
				if (answer.equals(Commands.INITIALIZE)) {
					this.clientView.displayMessage("Welcome to the server");
				}
				else {
					this.clientView.displayMessage("No initialization successful");
				}
			} catch (ServerUnavailableException e) {
				
				e.printStackTrace();
			}
			
		}
		/**
		 * A method that closes the connection also catches IOExceptions 
		 */
		public void closeConnection() {
			try {
				input.close();  //close the input stream
				output.close(); //close the output stream
				socket.close(); //close the socket
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * Reads and returns one line from the server
		 * @return the line that was sent by the server
		 * @throws ServerUnavailableException - in case an error during the IO phase occurs
		 */
		public String readLineFromServer() throws ServerUnavailableException{
			String response = " ";
			if(input != null) {
				try {
					response = input.readLine();
					if(response == null) {
						throw new ServerUnavailableException("Info from server could not be read");
					}
					return response; 
				} catch(IOException e) {
					throw new ServerUnavailableException("Info couljd not be read");
				}
			}
			else {
				throw new ServerUnavailableException("Info could not be read"); 
			}
		}
		
		/**
		 * used to communicate the move
		 * @param command the movement command
		 * @throws ServerUnavailableException 
		 */
		public void sendMove(String[] command) throws ServerUnavailableException{ 
			this.sendMesssage(Commands.MAKE_MOVE + Commands.DELIMITER + command[0] + Commands.DELIMITER + command[1]);
		}
		/**
		 * method used for communicating the disconnection
		 * @throws ServerUnavailableException
		 */
		public void disconnectFromLobby() throws ServerUnavailableException { 
			this.sendMesssage(Commands.DISCONNECT_LOBBY);
		}
		/**
		 * sends signal to the server that the client is ready to start a game
		 * @throws ServerUnavailableException
		 */
		public void startGame() throws ServerUnavailableException {
			this.sendMesssage(Commands.START);
		}
		
		/**
		 * 
		 * @param name - the name of the player
		 * @param amount - the amount of players - 2,3 or 4
		 * @throws ServerUnavailableException
		 */
		public void sendJoin(String name, int amount) throws ServerUnavailableException { 
			this.sendMesssage(Commands.JOIN + Commands.DELIMITER + name + Commands.DELIMITER + amount);
		}
		/**
		 * @requires param lobby is not null
		 * a method that handles the Lobby join
		 * @param lobby
		 */
		public void handleJoinLobby(String lobby) {
			this.clientView.displayMessage("Lobby joined: " + lobby);
		}
		
		/**
		 * resets the socket and sets the input and output streams to null
		 */
				public void Reset() {
					socket = null;
					input = null;
					output = null;
				}
		public static void main(String[] args) {
			Client client = new Client();
			client.startClient();
			Thread serverInputHandler = new Thread (client);
			serverInputHandler.start();
			try {
				client.clientView.start();
			} catch (ServerUnavailableException e) {
				e.printStackTrace();
			}
			
			
		}
		/**
		 * the run method listens for the server input and uses the displayMessage method from the ClientTUI to forward the messages
		 */
		@Override
		public void run() {
			boolean check = true; 
			while(check) {
				try {
					String inputFromServer = input.readLine();
					String[] inputSecond = inputFromServer.split(Commands.DELIMITER.toString());
					switch(inputSecond[0]) {
					case Commands.CONNECTED:
						this.handleJoinLobby(inputSecond[1]);
						break;
					case Commands.GAME:
						this.clientView.displayMessage("Your marbles are: " + inputSecond[1]);
					    break;
					case Commands.ACCEPT:
						this.clientView.displayMessage("The move request has been accepted");
						break;
					case Commands.DECLINE:
						this.clientView.displayMessage("Declined entry: " + inputSecond[1] );
						break;
					case Commands.DISCONNECT_LOBBY:
						this.clientView.displayMessage("Player " + " " + inputSecond[1] + "has disconnected"); 
					break; 
					case Commands.GAME_FINISH:
						this.clientView.displayMessage("The game is finished and the winner is: " + inputSecond[1]);
						break;
					default: 
						this.clientView.displayMessage("Unknown response" + inputSecond);
						break;
				}
			}catch (IOException e) {
				this.clientView.displayMessage("Server is unvailable!");
				check = false;
				}
			
			}
		}
}