package Game;

import java.util.ArrayList;

import Pieces.Marble;

public abstract class Board {
	private Marble[][] board;

	/**
	 * Initialize the board to 9 rows and 9 columns.
	 */
	public Board() {
		board = new Marble[9][9];
		initialize();
	}

	/**
	 * This method returns the two dimensional array of marble.
	 * 
	 * @return board
	 */
	public Marble[][] getBoard() {
		return board;
	}

	private void initialize() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new Marble("N/A", " ");
			}
		}
		board[0][0] =  new Marble("N/A", "");
		board[0][1] =  new Marble("N/A", "");
		board[0][7] =  new Marble("N/A", "");
		board[0][8] =  new Marble("N/A", "");
		board[1][0] =  new Marble("N/A", "");
		board[1][1] =  new Marble("N/A", "");
		board[1][8] =  new Marble("N/A", "");
		board[2][0] =  new Marble("N/A", "");
		board[2][8] =  new Marble("N/A", "");
		board[3][0] =  new Marble("N/A", "");
		board[5][0] =  new Marble("N/A", "");
		board[6][0] =  new Marble("N/A", "");
		board[6][8] =  new Marble("N/A", "");
		board[7][0] =  new Marble("N/A", "");
		board[7][1] =  new Marble("N/A", "");
		board[7][8] =  new Marble("N/A", "");
		board[8][0] =  new Marble("N/A", "");
		board[8][1] =  new Marble("N/A", "");
		board[8][7] =  new Marble("N/A", "");
		board[8][8] =  new Marble("N/A", "");
	}

	public abstract void fillBoard(ArrayList<Player> players);

	/**
	 * This method displays the board to the Players.
	 * 
	 * @param player
	 */
	public void displayBoard() {
		int whitespaces1 = 4;
		int whitespaces2 = 0;
		System.out.println("\t\t\t\t    -----------------------------");
		for (int i = 0; i < board.length; i++) {
			System.out.print("\t\t" + ((char) (65 + i)) + "\t");
			for (int k = 0; k < whitespaces1; k++) {
				System.out.print("   ");
			}
			whitespaces1--;
			if (i > 3) {
				for (int k = 0; k < whitespaces2; k++) {
					System.out.print("   ");
				}
				whitespaces2++;
			}
			for (int j = 0; j < board[i].length; j++) {
				if (isValidPosition(i, j)) {
					System.out.print("| " + board[i][j].getSymbol() + " | ");
				}
			}
			if (i == 0 || i == 8) {
				System.out.print(" (1-5)");
			} else if (i == 1 || i == 7) {
				System.out.print(" (1-6)");
			} else if (i == 2 || i == 6) {
				System.out.print(" (1-7)");
			} else if (i == 3 || i == 5) {
				System.out.print(" (1-8)");
			} else if (i == 4) {
				System.out.print(" (1-9)");
			}
			System.out.print("\n\t\t\t");
			for (int j = 0; j < 53; j++) {
				if (i == 0) {
					if (j > 11 && j < 41) {
						System.out.print("-");
					} else {
						System.out.print(" ");
					}
				} else if (i == 1) {
					if (j > 8 && j < 44) {
						System.out.print("-");
					} else {
						System.out.print(" ");
					}
				} else if (i == 2) {
					if (j > 5 && j < 47) {
						System.out.print("-");
					} else {
						System.out.print(" ");
					}
				} else if (i == 3) {
					if (j > 2 && j < 50) {
						System.out.print("-");
					} else {
						System.out.print(" ");
					}
				} else if (i == 4) {
					System.out.print("-");
				} else if (i == 5) {
					if (j > 2 && j < 50) {
						System.out.print("-");
					} else {
						System.out.print(" ");
					}
				} else if (i == 6) {
					if (j > 5 && j < 47) {
						System.out.print("-");
					} else {
						System.out.print(" ");
					}
				} else if (i == 7) {
					if (j > 8 && j < 44) {
						System.out.print("-");
					} else {
						System.out.print(" ");
					}
				} else if (i == 8) {
					if (j > 11 && j < 41) {
						System.out.print("-");
					} else {
						System.out.print(" ");
					}
				}
			}
			System.out.println();
		}
	}

	public boolean isValidPosition(int row, int column) {
		if (row == 0 && column == 0) {
			return false;
		} else if (row == 0 && column == 1) {
			return false;
		} else if (row == 0 && column == 7) {
			return false;
		} else if (row == 0 && column == 8) {
			return false;
		} else if (row == 1 && column == 0) {
			return false;
		} else if (row == 1 && column == 1) {
			return false;
		} else if (row == 1 && column == 8) {
			return false;
		} else if (row == 2 && column == 0) {
			return false;
		} else if (row == 2 && column == 8) {
			return false;
		} else if (row == 3 && column == 0) {
			return false;
		} else if (row == 5 && column == 0) {
			return false;
		} else if (row == 6 && column == 0) {
			return false;
		} else if (row == 6 && column == 8) {
			return false;
		} else if (row == 7 && column == 0) {
			return false;
		} else if (row == 7 && column == 1) {
			return false;
		} else if (row == 7 && column == 8) {
			return false;
		} else if (row == 8 && column == 0) {
			return false;
		} else if (row == 8 && column == 1) {
			return false;
		} else if (row == 8 && column == 7) {
			return false;
		} else if (row == 8 && column == 8) {
			return false;
		} else if (row < 0) {
			return false;
		} else if (row > 8) {
			return false;
		} else if (column < 0) {
			return false;
		} else if (column > 8) {
			return false;
		}
		return true;
	}

	
	
	}
