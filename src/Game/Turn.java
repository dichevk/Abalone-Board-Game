package Game;

import java.util.ArrayList;

public class Turn {

	private ArrayList<Player> players;
	private int turn;
	private int moves;

	public Turn(ArrayList<Player> players) {
		this.players = players;
		turn = 0;
		moves = 0;
	}

	/**
	 * This method is used to get current turn of player
	 * 
	 * @return index of current player
	 */
	public int currentTurn() {
		return turn;
	}

	/**
	 * This method is used to get next player turn.
	 * 
	 * @return
	 */
	public int nextTurn() {
		moves++;
		turn++;
		if (turn == players.size()) {
			turn = 0;
		}
		return turn;
	}

	public int getMoves() {
		return moves;
	}
}