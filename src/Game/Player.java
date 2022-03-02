package Game;

import Pieces.Marble;

public class Player {
	private String name;
	private String type;
	private Marble marble;
	/**
	 * Set the type of the Player.
	 * 
	 * @param type
	 */

	public Player(String type) {
		this.type = type;
	}

	/**
	 * This method get the type of the player i.e. whether it is white or black.
	 * 
	 * @return type of player
	 */

	public String getType() {
		return type;
	}

	/**
	 * This method sets the type of the Player
	 * 
	 * @param type of Player
	 */
	public void setType(String type) {
		this.type = type;
	}
	 public Player(String name, Marble marble) {
	        this.name = name;
	        this.marble = marble;
	    }

	    public String getName() {
	        return this.name;
	    }

	    public Marble getMarble() {
	        return this.marble;
	    }
}
