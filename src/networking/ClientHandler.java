package networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * ClientHandler class for handling the communication for a single client
 * @author Warmaster
 * 
 */
public class ClientHandler implements Runnable{

	
	private BufferedReader input; 
	private BufferedWriter output; 
	private Socket socket; 
	private Server server; 
	private String name; 
	private int amount; 
	/**
	 * constructor for the class ClientHandler
	 * @param socket - the client socket
	 * @param server - the connected server
	 * @param name - the name of the client handler
	 */
	public ClientHandler(Socket socket, Server server, String name) {
		this.name = name; 
		this.socket = socket; 
		this.server = server; 
		try {
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleCommand(String msg) throws IOException{ 
		String[] splitting = msg.split(Commands.DELIMITER);
		String comm =" ";//initialize the command 
		String[]appendice = new String[2];
		if(splitting.length ==1){
			comm = splitting[0];
		}else {
			comm = splitting[0];
			appendice[0] = splitting[1];
			appendice[1] = splitting[2];
			
			
		}
		switch(comm) {
		case Commands.INITIALIZE:
			this.output.write(this.server.handleHello()); 
			this.output.newLine();
			this.output.flush();
			break;
		case Commands.JOIN:
			this.server.handleJoin(appendice, this.socket);
			break;
		/*case Commands.DISCONNECT_LOBBY:
			this.server.disconnectFromLobby(this.socket);
			break;*/
		case Commands.START:
			this.server.handleStart(this.socket);
			break;
		default: 
			this.output.write("Invalid request");
			this.output.flush();
			break;
			}
		}
		/**
		 * a method that shuts down the connection to this client
		 */
		  public void shutdown() {
		        System.out.println("> [" + name + "] Shutting down.");
		        try {
		            input.close(); // close the input stream 
		            output.close(); //close the output stream
		            socket.close(); //close the socket 
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        this.server.removeClient(this);
		    }

		/**
		 * the following classes are getters and setters for the instance variables we defined in this class
		 * @return the corresponding set instance variable
		 */

		    public String getName() {
		        return this.name;
		    }    
		    public void setAmount(int amount) {
		        this.amount = amount;	
		    }
		    public int getAmount() {
		        return this.amount;
		    }
		    public BufferedReader getInput() {
		    	return this.input;
		    }
		    public BufferedWriter getOutput() {
		        return this.output;
		    }

		    public Socket getSocket() {
		        return this.socket;
		    }
	@Override
	public void run() {
		String message; 
		try {
			message = input.readLine();
			while(message != null) {
				System.out.println(name + " " + message);
				handleCommand(message);
				message = input.readLine();
			}
		} catch (IOException e) {
			shutdown();
		}
		
	}

}
