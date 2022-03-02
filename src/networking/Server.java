package networking;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Exceptions.*;
import Game.Game;

import Game.*;

/**
 * 
 * @author Warmaster
 * chat and leaderboard are optional commands
 */

public class Server implements Runnable {
	public enum Marbles{ 
		W,
		B,
		G,
		R;
	}
	private ServerSocket serverSocket; 
	private Game game; 
	private Game gameForTwo; 
	private Game gameForThree; 
	private Game gameForFour; 
	private int numOfClient;
	private ArrayList<ClientHandler> clients; //list of client handlers. Each client gets a client handler
	private ArrayList<OnlinePlayer> players; //list of players
	public static final String NAME = "AbaloneServer";
	public static final Marbles[] MARBLES = { Marbles.B, Marbles.W, Marbles.G, Marbles.R};	
	private ServerTUI serverTUI; 
	public Server() {
		this.players = new ArrayList<>();
		this.clients = new ArrayList<>();
		this.numOfClient = 0; 
		serverTUI = new ServerTUI();
	}
	
	public String handleHello() {
		return Commands.INITIALIZE;
	}
	/**
	 * this method opens a new ServerSocket on a user-defined port. 
	 * The user has to input a port 
	 * attempt at opening a socket is made - if attempt:fails -> ask user to try again
	 */
	public void setup() throws ExitProgramException {
		
		serverSocket = null;
		while (serverSocket == null) {
			int port = serverTUI.getInt("Please enter the server port.");

			// try to open a new ServerSocket
			try {
				serverTUI.showMessage("Attempting to open a socket at 127.0.0.1 " + "on port " + port + "...");
				serverSocket = new ServerSocket(port, 0, InetAddress.getByName("127.0.0.1"));
				serverTUI.showMessage("Server started at port " + port);
			} catch (IOException e) {
				serverTUI.showMessage("ERROR: could not create a socket on " + "127.0.0.1" + " and port " + port + ".");

				if (!serverTUI.getBoolean("Do you want to try again?")) {
					throw new ExitProgramException("User indicated to exit the " + "program.");
				}
			}
		}
	}
	
	  public void run() {
		    boolean openNewSocket = true;
		    while (openNewSocket) {
		      try {
		        setup();
		        while (true) {
		          Socket sock = this.serverSocket.accept();
		          String name = "Client " + String.format("%02d", new Object[] { Integer.valueOf(this.clients.size() + 1) });
		          this.serverTUI.showMessage("New client [" + name + "] connected!");
		          ClientHandler handler = new ClientHandler(sock, this, name);
		          new Thread(handler).start();
		          this.clients.add(handler);
		        } 
		      } catch (ExitProgramException e1) {
		        openNewSocket = false;
		      } catch (IOException e) {
		        System.out.println("A server IO error occurred: " + e.getMessage());
		        if (!this.serverTUI.getBoolean("Do you want to open a new socket?"))
		          openNewSocket = false; 
		      } 
		    } 
		    this.serverTUI.showMessage("See you later!");
		  }
		  
	/**
	 * a method for the handshake
	 * 
	 * @return
	 */
	public String initialize() {
	        return Commands.INITIALIZE + Commands.DELIMITER + NAME;
	    }

	
	/**
	 * this method handles the start of the game, it should wait for all the clients's information
	 * After which, the game starts and is passed to the clients in the lobby 
	 */
	public synchronized void handleStart(Socket socket) {
		//to do
	}
	
	 /**
     * Removes a clientHandler from the client list.
     * @requires client != null
     */
    
    public void removeClient(ClientHandler client) {
        this.clients.remove(client);
    }
	/**
	 * a method for writing to the output. IOExceptions are handled. 
	 * @requires output entry is not null 
	 * @requires message entry is not null
	 * @param output
	 * @param message
	 */
	
	public synchronized void handleMove(String[] move, Socket socket) {
		//to do 
		
		
	}
	  public synchronized void handleJoin(String[] tail, Socket sock) {
	        BufferedWriter out = null;
	        for (int j = 0; j < this.clients.size(); j++) {
	            if (this.clients.get(j).getSocket().equals(sock)) {
	                this.clients.get(j).setAmount(Integer.parseInt(tail[1]));
	                out = this.clients.get(j).getOutput();
	            }
	        }
	        for (int i = 0; i < this.players.size(); i++) {
	            if (this.players.get(i).getName().equals(tail[0])) {
	                send(out, "The chosen name is taken");
	            	}
	        	}
	  		}
	  
	  /** 
	   * check for game for 2 players, game for 3 players, game for 4 players, use the constructors for move and push for the movement
	   * @param IPAddress
	   */
	  public void handleReady(InetAddress IPAddress) {
		  {
				 //switch case 
				switch(players.size()) {
				case 2: 
					game = new Game();
					// create a game for two using the boardForTwo
				case 3: 
					//create a game for three players using the boardForThree 
				case 4: 
					//create a game for four players using the boardForFour
				}
			}
	  }
	  
	  public void send(BufferedWriter out, String message) {
			try {
				System.out.println("[outgoing]: " + message);
				out.write(message);
				out.newLine();
				out.flush();
			} catch (IOException e) {
			}
		}
	  public void sendAll(String message) {
			for (int i = 0; i < clients.size(); i++) {
				send(clients.get(i).getOutput(), message);
			}
		}
	  /**
	   * 
	   * @return a String that represents all position from the board and what marble they contain
	   * @throws IOException
	   */
	 // public String syncBoard() throws IOException{
		  
	 // }
		public String sendEndTurn(InetAddress clientIP) {
			// TODO Auto-generated method stub
			return "" + Commands.END_TURN;
		}
	 

	/**
	 * method that sends a message to the clients when the game is over. 
	 */
	  
	public void GameOver() {
		
	}
	public void disconnectFromLobby(InetSocketAddress socketaddr) {
    for (int i = 0; i < clients.size(); i++) {
        if (clients.get(i).getSocket().equals(socketaddr)) {
            for (int j = 0; j < players.size(); j++) {
                if (players.get(j).getPort() == socketaddr.getPort()) {
                    String name = players.get(j).getName();
                    int amount = clients.get(i).getAmount();
                    clients.remove(clients.get(i));
                    sendAll(Commands.DISCONNECT_LOBBY + Commands.DELIMITER + name+amount);
                    players.remove(i); // remove the player from the list; 
                		}
            		}
        		}
    		}
		}
	public static void main(String[]args) {
		Server server1 = new Server();
		new Thread(server1).start();
	}
	}