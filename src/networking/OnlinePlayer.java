package networking;

import java.io.BufferedWriter;
import java.net.InetAddress;

import Game.Player;
import Pieces.Marble;

public class OnlinePlayer extends Player {
	private InetAddress IPAddress; 
	private BufferedWriter output; 
	private Marble marble; 
	private int port; 
	private String name; 
	boolean start = false; 
	/**
	 * constructor for the onlinePlayer 
	 * @param name
	 * @param output
	 * @param port
	 * @param IPAddress
	 */
	 public OnlinePlayer(String name, BufferedWriter output, int port, InetAddress IPAddress) {
		 super(name,null);
		 this.IPAddress = IPAddress;
		 this.output = output; 
		 this.port = port; 
		  
	 }
	 /**
	  * setters and getters following: 
	  * @param marble
	  */
	 public void setMarble(Marble marble) {
	        this.marble = marble;
	        
	    }
	 public void setStart() {
	        this.start = true; 
	    }
	 public InetAddress getIp() {
	        return this.IPAddress;
	    }

	    public BufferedWriter getWriter() {
	        return this.output;
	    }

	    public boolean getStart() {
	        return this.start;
	    }

	   
	    
	    public Marble getMarble() {
	        return this.marble;
	    }
	    public int getPort() {
	        return this.port;
	    }
		
	    
	    //additionally needs a way to initialize the movement given the board, marbles and direction 
}
