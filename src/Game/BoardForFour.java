package Game;

import java.util.ArrayList;

import Pieces.Marble;

public class BoardForFour extends Board {
	@Override
	public void fillBoard(ArrayList<Player> players) {
		getBoard()[0][2] = new Marble("Red", "R");
		getBoard()[0][3] = new Marble("Red", "R");
		getBoard()[0][4] = new Marble("Red", "R");
		getBoard()[0][5] = new Marble("Red", "R");
		getBoard()[1][3] = new Marble("Red", "R");
		getBoard()[1][4] = new Marble("Red", "R");
		getBoard()[1][5] = new Marble("Red", "R");
		getBoard()[2][3] = new Marble("Red", "R");
		getBoard()[2][4] = new Marble("Red", "R");

		getBoard()[1][7] = new Marble("Black", "B");
		getBoard()[2][6] = new Marble("Black", "B");
		getBoard()[2][7] = new Marble("Black", "B");
		getBoard()[3][6] = new Marble("Black", "B");
		getBoard()[3][7] = new Marble("Black", "B");
		getBoard()[3][8] = new Marble("Black", "B");
		getBoard()[4][6] = new Marble("Black", "B");
		getBoard()[4][7] = new Marble("Black", "B");
		getBoard()[4][8] = new Marble("Black", "B");

		getBoard()[4][0] = new Marble("White", "W");
		getBoard()[4][1] = new Marble("White", "W");
		getBoard()[4][2] = new Marble("White", "W");
		getBoard()[5][1] = new Marble("White", "W");
		getBoard()[5][2] = new Marble("White", "W");
		getBoard()[5][3] = new Marble("White", "W");
		getBoard()[6][1] = new Marble("White", "W");
		getBoard()[6][2] = new Marble("White", "W");
		getBoard()[7][2] = new Marble("White", "W");

		getBoard()[6][4] = new Marble("Green", "G");
		getBoard()[6][5] = new Marble("Green", "G");
		getBoard()[7][4] = new Marble("Green", "G");
		getBoard()[7][5] = new Marble("Green", "G");
		getBoard()[7][6] = new Marble("Green", "G");
		getBoard()[8][3] = new Marble("Green", "G");
		getBoard()[8][4] = new Marble("Green", "G");
		getBoard()[8][5] = new Marble("Green", "G");
		getBoard()[8][6] = new Marble("Green", "G");

	}
}