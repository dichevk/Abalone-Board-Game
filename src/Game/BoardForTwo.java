package Game;

import java.util.ArrayList;

import Pieces.Marble;

public class BoardForTwo extends Board {
	@Override
	public void fillBoard(ArrayList<Player> players) {
		for (int i = 0; i < getBoard().length; i++) {
			for (int j = 0; j < getBoard()[i].length; j++) {
				if (isValidPosition(i, j)) {
					if (i == 0 || i == 1) {
						getBoard()[i][j] = new Marble("Black", "B");
					} else if (i == 2 && j > 2 && j < 6) {
						getBoard()[i][j] = new Marble("Black", "B");
					} else if (i == 7 || i == 8) {
						getBoard()[i][j] = new Marble("White", "W");
					} else if (i == 6 && j > 2 && j < 6) {
						getBoard()[i][j] = new Marble("White", "W");
					}

				}
			}
		}
	}
}