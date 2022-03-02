package Game;

import java.util.ArrayList;

import Pieces.Marble;

public class BoardForThree extends Board {
	@Override
	public void fillBoard(ArrayList<Player> players) {
		getBoard()[0][2] = new Marble("White", "W");
		getBoard()[0][3] = new Marble("White", "W");
		getBoard()[1][2] = new Marble("White", "W");
		getBoard()[1][3] = new Marble("White", "W");
		getBoard()[2][1] = new Marble("White", "W");
		getBoard()[2][2] = new Marble("White", "W");
		getBoard()[3][1] = new Marble("White", "W");
		getBoard()[3][2] = new Marble("White", "W");
		getBoard()[4][0] = new Marble("White", "W");
		getBoard()[4][1] = new Marble("White", "W");
		getBoard()[5][1] = new Marble("White", "W");
		
		getBoard()[0][5] = new Marble("Black", "B");
		getBoard()[0][6] = new Marble("Black", "B");
		getBoard()[1][6] = new Marble("Black", "B");
		getBoard()[1][7] = new Marble("Black", "B");
		getBoard()[2][6] = new Marble("Black", "B");
		getBoard()[2][7] = new Marble("Black", "B");
		getBoard()[3][7] = new Marble("Black", "B");
		getBoard()[3][8] = new Marble("Black", "B");
		getBoard()[4][7] = new Marble("Black", "B");
		getBoard()[4][8] = new Marble("Black", "B");
		getBoard()[5][8] = new Marble("Black", "B");
		
		for (int i = 0; i < getBoard().length; i++) {
			for (int j = 0; j < getBoard()[i].length; j++) {
				if (isValidPosition(i, j)) {
					if (i == 7 || i == 8) {
						getBoard()[i][j] = new Marble("Red", "R");
					}
				}
			}
		}
	}
}