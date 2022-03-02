package Game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Pieces.Marble;

public class Game {

	private Board board;
	private Scanner scanner;
	private ArrayList<Player> players;
	private Turn turn;
	private int row;
	private int column;
	private boolean isDiagonal;
	private boolean twoMarbleMove;
	private ArrayList<Integer> indexes;

	public Game() {
		scanner = new Scanner(System.in);
		players = new ArrayList<Player>();
		indexes = new ArrayList<Integer>();
		row = -1;
		column = -1;
		isDiagonal = false;
	}

	/**
	 * This method is used to setup the game i.e. initialize players board.
	 */
	public void setup() {

		System.out.println("\t\t\t******************************************************");
		System.out.println("\t\t\t*********************  Abalone  **********************");
		System.out.println("\t\t\t******************************************************");
		int numberOfPlayers = 0;
		do {
			numberOfPlayers = takeIntegerInput(scanner, "Enter Number of Players (2,3,4): ");
			Player player1 = null;
			Player player2 = null;
			Player player3 = null;
			Player player4 = null;
			switch (numberOfPlayers) {
			case 2:
				board = new BoardForTwo();
				player1 = new Player("Black");
				player2 = new Player("White");
				players.add(player1);
				players.add(player2);
				break;
			case 3:
				board = new BoardForThree();
				player1 = new Player("White");
				player2 = new Player("Black");
				player3 = new Player("Red");
				players.add(player1);
				players.add(player2);
				players.add(player3);
				break;
			case 4:
				board = new BoardForFour();
				player1 = new Player("Red");
				player2 = new Player("Black");
				player3 = new Player("Green");
				player4 = new Player("White");
				players.add(player1);
				players.add(player2);
				players.add(player3);
				players.add(player4);
				break;
			default:
				numberOfPlayers = 0;
				break;
			}
		} while (numberOfPlayers == 0);
		board.fillBoard(players);
		turn = new Turn(players);
		board.displayBoard();
	}

	/**
	 * This method is used to play the game.
	 */
	public void play() {
		String move;
		String position;
		Direction direction;

		int playerIndex = turn.currentTurn();
		do {
			System.out.println("\n" + players.get(playerIndex).getType() + "'s Turn");
			move = takeMoveInput();
			position = takePositionInput(move);
			direction = takeDirectionInput();
			if (move.equals("move")) {
				row = getRowIndex(position);
				column = getColumnIndex(position);
				if (!board.getBoard()[row][column].getColor().equals(players.get(playerIndex).getType())) {
					System.out.println("\nPlease select correct piece.\n");
				} else {
					if (validateMove(direction, position)) {
						Marble marble = createNewMarble(players.get(playerIndex));
						board.getBoard()[row][column] = marble;
						playerIndex = turn.nextTurn();
					} else {
						System.out.println("Invalid Move.\n");
					}
				}
			} else {
				boolean check = isValidColumn(position);
				if (check) {
					System.out.println("Column Input validated");
					setIndexes(position);
					
					playerIndex = turn.nextTurn();
				} else {
					System.out.println("Invalid Move");
				}

			}
			board.displayBoard();
		} while (turn.getMoves() < 96);
	}

	public void setIndexes(String position) {

		String[] tokens = position.split(" ");
		String[] token1 = tokens[0].split("-");
		String[] token2 = tokens[1].split("-");
		String columnValue1 = token1[1];
		String columnValue2 = token2[1];
		int digitValue1 = Integer.parseInt(columnValue1);
		int digitValue2 = Integer.parseInt(columnValue2);

		int difference = digitValue1 - digitValue2;

		int row1 = getRowIndex(tokens[0]);

		String letter1 = token1[0];
		String letter2 = token2[0];

		int digitValue3 = letter1.charAt(0);
		int digitValue4 = letter2.charAt(0);

		int difference2 = digitValue3 - digitValue4;

		indexes.add(getRowIndex(tokens[0]));
		indexes.add(getColumnIndex(tokens[0]));

//		if (!twoMarbleMove) {
//			if (difference2 != 0) {
//				
//			}
//			if (row1 % 2 == 0) {
//				if (difference2 >= 1) {
//					if (difference == -1) {
//						indexes.add((getRowIndex(tokens[0])) - 1);
//						indexes.add(getColumnIndex(tokens[1]));
//					} else if (difference == 1) {
//						indexes.add((getRowIndex(tokens[0])) - 1);
//						indexes.add(getColumnIndex(tokens[0]));
//					}
//				} else if (difference2 < 0) {
//					if (difference == -1) {
//						indexes.add((getRowIndex(tokens[0])) - 1);
//						indexes.add(getColumnIndex(tokens[1]));
//					} else if (difference == 1) {
//						indexes.add((getRowIndex(tokens[0])) - 1);
//						indexes.add(getColumnIndex(tokens[0]));
//					}
//				}
//
//			}
//		}

		indexes.add(getRowIndex(tokens[1]));
		indexes.add(getColumnIndex(tokens[1]));
	}

	public boolean isValidColumn(String position) {
		String[] tokens = position.split(" ");
		String[] token1 = tokens[0].split("-");
		String[] token2 = tokens[1].split("-");
		String columnValue1 = token1[1];
		String columnValue2 = token2[1];

		int digitValue1 = Integer.parseInt(columnValue1);
		int digitValue2 = Integer.parseInt(columnValue2);

		int difference = digitValue1 - digitValue2;
		difference = Math.abs(difference);

		String letter1 = token1[0];
		String letter2 = token2[0];

		int digitValue3 = letter1.charAt(0);
		int digitValue4 = letter2.charAt(0);

		int difference2 = digitValue3 - digitValue4;
		difference2 = Math.abs(difference2);

		if (difference == 0 && difference2 == 1) {
			twoMarbleMove = true;
			return true;
		} else if (difference == 0 && difference2 == 2) {
			twoMarbleMove = false;
			return true;
		} else if (difference == 1 && difference2 == 1) {
			twoMarbleMove = true;
			isDiagonal = true;
			return true;
		} else if (difference == 2 && difference2 == 1) {
			twoMarbleMove = false;
			isDiagonal = true;
			return true;
		} else if (difference == 1 && difference2 == 0) {
			isDiagonal = true;
			return true;
		}

		return false;
	}

	public int columnDifference(String position) {
		String[] tokens = position.split(" ");
		String[] token1 = tokens[0].split("-");
		String[] token2 = tokens[1].split("-");
		String columnValue1 = token1[1];
		String columnValue2 = token2[1];

		int digitValue1 = Integer.parseInt(columnValue1);
		int digitValue2 = Integer.parseInt(columnValue2);

		int difference = digitValue1 - digitValue2;
		return difference;
	}

	/**
	 * This method is used to calculate the row difference according to position of
	 * marbles if it is a column move
	 * 
	 * @param position
	 * @return difference of rows
	 */
	public int rowDifference(String position) {
		String[] tokens = position.split(" ");
		String[] token1 = tokens[0].split("-");
		String[] token2 = tokens[1].split("-");
		String letter1 = token1[0];
		String letter2 = token2[0];

		int digitValue1 = letter1.charAt(0);
		int digitValue2 = letter2.charAt(0);

		int difference = digitValue1 - digitValue2;
		return difference;
	}

	/**
	 * This method is used to create new marble according to player.
	 * 
	 * @param player
	 * @return new marble
	 */
	public Marble createNewMarble(Player player) {
		if (player.getType().equals("Red")) {
			return new Marble("Red", "R");
		} else if (player.getType().equals("White")) {
			return new Marble("White", "W");
		} else if (player.getType().equals("Black")) {
			return new Marble("Black", "B");
		} else if (player.getType().equals("Green")) {
			return new Marble("Green", "G");
		}
		return null;
	}

	/**
	 * This method is used to validate column move according to position and
	 * direction.
	 * 
	 * @param direction
	 * @param position
	 * @return true if valid move otherwise false
	 */
//	public boolean validateMoveCInput(Direction direction, String position) {
//		String[] tokens = position.split("-");
//		String letter = tokens[0];
//
//		if (Direction.LEFT.getValue() == direction.getValue() || Direction.RIGHT.getValue() == direction.getValue()) {
//			if (letter.equals("A") || letter.equals("I")) {
//				if (column >= 3 && column <= 5) {
//					return true;
//				}
//			} else if (letter.equals("B") || letter.equals("H")) {
//				if (column >= 3 && column <= 6) {
//					return true;
//				}
//			} else if (letter.equals("C") || letter.equals("G")) {
//				if (column >= 2 && column <= 6) {
//					return true;
//				}
//			} else if (letter.equals("D") || letter.equals("F")) {
//				if (column >= 2 && column <= 7) {
//					return true;
//				}
//			} else if (letter.equals("E")) {
//				if (column >= 1 && column <= 7) {
//					return true;
//				}
//			}
//		}
//
//		if (Direction.UPLEFT.getValue() == direction.getValue()) {
//			if (row >= 1 && row <= 7) {
//				if (letter.equals("B")) {
//					if (column >= 3 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("C")) {
//					if (column >= 2 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("D")) {
//					if (column >= 2 && column <= 8) {
//						return true;
//					}
//				} else if (letter.equals("E") || letter.equals("F")) {
//					if (column >= 1 && column <= 8) {
//						return true;
//					}
//				} else if (letter.equals("G")) {
//					if (column >= 1 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("H")) {
//					if (column >= 2 && column <= 7) {
//						return true;
//					}
//				}
//			}
//		}
//
//		if (Direction.UPRIGHT.getValue() == direction.getValue()) {
//			if (row >= 1 && row <= 7) {
//				if (letter.equals("B")) {
//					if (column >= 2 && column <= 6) {
//						return true;
//					}
//				} else if (letter.equals("C")) {
//					if (column >= 1 && column <= 6) {
//						return true;
//					}
//				} else if (letter.equals("D")) {
//					if (column >= 1 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("E")) {
//					if (column >= 1 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("F")) {
//					if (column >= 2 && column <= 8) {
//						return true;
//					}
//				} else if (letter.equals("G")) {
//					if (column >= 2 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("H")) {
//					if (column >= 3 && column <= 7) {
//						return true;
//					}
//				}
//			}
//		}
//		if (Direction.DOWNRIGHT.getValue() == direction.getValue()) {
//			if (row >= 1 && row <= 7) {
//				if (letter.equals("B")) {
//					if (column >= 3 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("C")) {
//					if (column >= 2 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("D")) {
//					if (column >= 2 && column <= 8) {
//						return true;
//					}
//				} else if (letter.equals("E") || letter.equals("F")) {
//					if (column >= 1 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("G")) {
//					if (column >= 1 && column <= 6) {
//						return true;
//					}
//				} else if (letter.equals("H")) {
//					if (column >= 2 && column <= 6) {
//						return true;
//					}
//				}
//			}
//		}
//		if (Direction.DOWNLEFT.getValue() == direction.getValue()) {
//			if (row >= 1 && row <= 7) {
//				if (letter.equals("B")) {
//					if (column >= 2 && column <= 6) {
//						return true;
//					}
//				} else if (letter.equals("C")) {
//					if (column >= 1 && column <= 6) {
//						return true;
//					}
//				} else if (letter.equals("D") || letter.equals("E")) {
//					if (column >= 1 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("F")) {
//					if (column >= 2 && column <= 8) {
//						return true;
//					}
//				} else if (letter.equals("G")) {
//					if (column >= 2 && column <= 7) {
//						return true;
//					}
//				} else if (letter.equals("H")) {
//					if (column >= 3 && column <= 7) {
//						return true;
//					}
//				}
//			}
//		}
//
//		return false;
//	}

	/**
	 * This method is used validate move according to position and direction. If it
	 * is a valid move return true otherwise false
	 * 
	 * @param direction
	 * @param position
	 * @return true if valid move otherwise false
	 */
	public boolean validateMove(Direction direction, String position) {

		if (Direction.UPLEFT.getValue() == direction.getValue() && row != 0 && column != 0) {

			if (row == 1 && column >= 3 && column <= 7) {
				if (board.getBoard()[row - 1][column - 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					column = column - 1;
					return true;
				}
			} else if (row == 2 && column >= 2 && column <= 7) {
				if (board.getBoard()[row - 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					return true;
				}
			} else if (row == 3 && column >= 2 && column <= 8) {
				if (board.getBoard()[row - 1][column - 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					column = column - 1;
					return true;
				}
			} else if (row == 4 && column >= 1 && column <= 8) {
				if (board.getBoard()[row - 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					return true;
				}
			} else if (row == 5 && column >= 1 && column <= 8) {
				if (board.getBoard()[row - 1][column - 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					column = column - 1;
					return true;
				}
			} else if (row == 6 && column >= 1 && column <= 7) {
				if (board.getBoard()[row - 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					return true;
				}
			} else if (row == 7 && column >= 2 && column <= 7) {
				if (board.getBoard()[row - 1][column - 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					column = column - 1;
					return true;
				}
			} else if (row == 8 && column >= 2 && column <= 6) {
				if (board.getBoard()[row - 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					return true;
				}
			}

		} else if (Direction.UPRIGHT.getValue() == direction.getValue() && row != 0 && column != 8) {

			if (row == 1 && column >= 2 && column <= 6) {
				if (board.getBoard()[row - 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					return true;
				}
			} else if (row == 2 && column >= 1 && column <= 6) {
				if (board.getBoard()[row - 1][column + 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					column = column + 1;
					return true;
				}
			} else if (row == 3 && column >= 1 && column <= 7) {
				if (board.getBoard()[row - 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					return true;
				}
			} else if (row == 4 && column >= 0 && column <= 7) {
				if (board.getBoard()[row - 1][column + 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					column = column + 1;
					return true;
				}
			} else if (row == 5 && column >= 1 && column <= 8) {
				if (board.getBoard()[row - 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					return true;
				}
			} else if (row == 6 && column >= 1 && column <= 7) {
				if (board.getBoard()[row - 1][column + 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					column = column + 1;
					return true;
				}
			} else if (row == 7 && column >= 2 && column <= 7) {
				if (board.getBoard()[row - 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					return true;
				}
			} else if (row == 8 && column >= 2 && column <= 6) {
				if (board.getBoard()[row - 1][column + 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row - 1;
					column = column + 1;
					return true;
				}
			}
		} else if (Direction.DOWNLEFT.getValue() == direction.getValue() && row != 8 && column != 0) {

			if (row == 0 && column >= 2 && column <= 6) {
				if (board.getBoard()[row + 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					return true;
				}
			} else if (row == 1 && column >= 2 && column <= 7) {
				if (board.getBoard()[row + 1][column - 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					column = column - 1;
					return true;
				}
			} else if (row == 2 && column >= 1 && column <= 7) {
				if (board.getBoard()[row + 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					return true;
				}
			} else if (row == 3 && column >= 1 && column <= 8) {
				if (board.getBoard()[row + 1][column - 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					column = column - 1;
					return true;
				}
			} else if (row == 4 && column >= 1 && column <= 8) {
				if (board.getBoard()[row + 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					return true;
				}
			} else if (row == 5 && column >= 2 && column <= 8) {
				if (board.getBoard()[row + 1][column - 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					column = column - 1;
					return true;
				}
			} else if (row == 6 && column >= 2 && column <= 7) {
				if (board.getBoard()[row + 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					return true;
				}
			} else if (row == 7 && column >= 3 && column <= 7) {
				if (board.getBoard()[row + 1][column - 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					column = column - 1;
					return true;
				}
			}

		} else if (Direction.DOWNRIGHT.getValue() == direction.getValue() && row != 8 && column != 8) {

			if (row == 0 && column >= 2 && column <= 6) {
				if (board.getBoard()[row + 1][column + 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					column = column + 1;
					return true;
				}
			} else if (row == 1 && column >= 2 && column <= 7) {
				if (board.getBoard()[row + 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					return true;
				}

			} else if (row == 2 && column >= 1 && column <= 7) {
				if (board.getBoard()[row + 1][column + 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					column = column + 1;
					return true;
				}
			} else if (row == 3 && column >= 1 && column <= 8) {
				if (board.getBoard()[row + 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					return true;
				}
			} else if (row == 4 && column >= 0 && column <= 7) {
				if (board.getBoard()[row + 1][column + 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					column = column + 1;
					return true;
				}
			} else if (row == 5 && column >= 1 && column <= 7) {
				if (board.getBoard()[row + 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					return true;
				}
			} else if (row == 6 && column >= 1 && column <= 6) {
				if (board.getBoard()[row + 1][column + 1].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					column = column + 1;
					return true;
				}
			} else if (row == 7 && column >= 2 && column <= 6) {
				if (board.getBoard()[row + 1][column].getSymbol().equals(" ")) {
					board.getBoard()[row][column] = new Marble("N/A", " ");
					row = row + 1;
					return true;
				}
			}

		} else if (Direction.LEFT.getValue() == direction.getValue() && column != 0
				&& board.getBoard()[row][column - 1].getSymbol().equals(" ")) {

			board.getBoard()[row][column] = new Marble("N/A", " ");
			column = column - 1;
			return true;

		} else if (Direction.RIGHT.getValue() == direction.getValue() && column != 8
				&& board.getBoard()[row][column + 1].getSymbol().equals(" ")) {

			board.getBoard()[row][column] = new Marble("N/A", " ");
			column = column + 1;
			return true;

		}

		return false;
	}

	/**
	 * This method is used get column index based on position
	 * 
	 * @param position
	 * @return column index
	 */
	public int getColumnIndex(String position) {
		String[] tokens = position.split("-");
		String letter = tokens[0];
		int digit = Integer.parseInt(tokens[1]);
		int index = -1;

		if (letter.equals("A") || letter.equals("I")) {
			if (digit == 1) {
				index = digit + 1;
			} else if (digit == 2) {
				index = digit + 1;
			} else if (digit == 3) {
				index = digit + 1;
			} else if (digit == 4) {
				index = digit + 1;
			} else if (digit == 5) {
				index = digit + 1;
			}
		} else if (letter.equals("B") || letter.equals("H")) {
			if (digit == 1) {
				index = digit + 1;
			} else if (digit == 2) {
				index = digit + 1;
			} else if (digit == 3) {
				index = digit + 1;
			} else if (digit == 4) {
				index = digit + 1;
			} else if (digit == 5) {
				index = digit + 1;
			} else if (digit == 6) {
				index = digit + 1;
			}
		} else if (letter.equals("C") || letter.equals("G")) {
			if (digit == 1) {
				index = digit;
			} else if (digit == 2) {
				index = digit;
			} else if (digit == 3) {
				index = digit;
			} else if (digit == 4) {
				index = digit;
			} else if (digit == 5) {
				index = digit;
			} else if (digit == 6) {
				index = digit;
			} else if (digit == 7) {
				index = digit;
			}
		} else if (letter.equals("D") || letter.equals("F")) {
			if (digit == 1) {
				index = digit;
			} else if (digit == 2) {
				index = digit;
			} else if (digit == 3) {
				index = digit;
			} else if (digit == 4) {
				index = digit;
			} else if (digit == 5) {
				index = digit;
			} else if (digit == 6) {
				index = digit;
			} else if (digit == 7) {
				index = digit;
			} else if (digit == 8) {
				index = digit;
			}
		} else if (letter.equals("E")) {
			if (digit == 1) {
				index = digit - 1;
			} else if (digit == 2) {
				index = digit - 1;
			} else if (digit == 3) {
				index = digit - 1;
			} else if (digit == 4) {
				index = digit - 1;
			} else if (digit == 5) {
				index = digit - 1;
			} else if (digit == 6) {
				index = digit - 1;
			} else if (digit == 7) {
				index = digit - 1;
			} else if (digit == 8) {
				index = digit - 1;
			} else if (digit == 9) {
				index = digit - 1;
			}
		}
		return index;
	}

	/**
	 * This method is used to take position as input and return the row index;
	 * 
	 * @param position
	 * @return row index
	 */
	public int getRowIndex(String position) {
		String[] tokens = position.split("-");
		String letter = tokens[0];
		int index = -1;
		if (letter.equals("A")) {
			index = 0;
		} else if (letter.equals("B")) {
			index = 1;
		} else if (letter.equals("C")) {
			index = 2;
		} else if (letter.equals("D")) {
			index = 3;
		} else if (letter.equals("E")) {
			index = 4;
		} else if (letter.equals("F")) {
			index = 5;
		} else if (letter.equals("G")) {
			index = 6;
		} else if (letter.equals("H")) {
			index = 7;
		} else if (letter.equals("I")) {
			index = 8;
		}
		return index;
	}

	/**
	 * This method is used to validate position of column of marbles.
	 * 
	 * @param input - range of positions of marbles
	 * @return true if correct input
	 */
	public boolean validateMoveCInput(String input) {
		String[] tokens = input.split(" ");
		if (tokens.length == 2) {
			if (!validateMoveInput(tokens[0])) {
				return false;
			}
			if (!validateMoveInput(tokens[1])) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * This method is used to take direction input. It keep on taking input until
	 * user enter correct input.
	 * 
	 * @return direction
	 */
	public Direction takeDirectionInput() {
		boolean flag = false;
		String choice;
		do {
			System.out.println("Enter direction: (left,right,upleft,upright,downleft,downright)");
			choice = scanner.nextLine();
			choice = choice.toLowerCase();
			if (choice.equals("left")) {
				return Direction.LEFT;
			} else if (choice.equals("right")) {
				return Direction.RIGHT;
			} else if (choice.equals("upleft")) {
				return Direction.UPLEFT;
			} else if (choice.equals("upright")) {
				return Direction.UPRIGHT;
			} else if (choice.equals("downleft")) {
				return Direction.DOWNLEFT;
			} else if (choice.equals("downright")) {
				return Direction.DOWNRIGHT;
			} else {
				flag = false;
			}
			if (!flag) {
				System.out.println("\nInvalid Input.\n");
			}
		} while (!flag);
		return null;
	}

	/**
	 * This method is also used to validate position i.e. Row A can contain only 1
	 * to 5 column.
	 * 
	 * @param row
	 * @param column
	 * @return true if validated otherwise false
	 */
	public boolean validatePosition(String row, int column) {
		if ((row.equals("A") || row.equals("I")) && column >= 1 && column <= 5) {
			return true;
		} else if ((row.equals("B") || row.equals("H")) && column >= 1 && column <= 6) {
			return true;
		} else if ((row.equals("C") || row.equals("G")) && column >= 1 && column <= 7) {
			return true;
		} else if ((row.equals("D") || row.equals("F")) && column >= 1 && column <= 8) {
			return true;
		} else if (row.equals("E") && column >= 1 && column <= 9) {
			return true;
		}
		return false;
	}

	/**
	 * This method is used to validate the position of marble input by user.
	 * 
	 * @param input the position of marble
	 * @return true if position is valid otherwise false
	 */
	public boolean validateMoveInput(String input) {
		String[] tokens = input.split("-");
		if (tokens.length == 2) {
			String row;
			int column;
			row = tokens[0];
			if (row.length() != 1) {
				return false;
			}
			if (!Character.isLetter(row.charAt(0))) {
				return false;
			}
			if (!Character.isUpperCase(row.charAt(0))) {
				return false;
			}
			try {
				column = Integer.parseInt(tokens[1]);
			} catch (Exception e) {
				return false;
			}
			if (column >= 10) {
				return false;
			}
			if (column <= 0) {
				return false;
			}
			if (!validatePosition(row, column)) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * This method is used to take position input i.e. the position of marble. It
	 * takes input according to moving one marble at a time or moving a column of
	 * marbles.
	 * 
	 * @param choice moving single marble or column of marbles
	 * @return position of marble(s)
	 */
	public String takePositionInput(String choice) {
		boolean flag = false;
		if (choice.equals("move")) {
			do {
				System.out.println("Enter position: (A-2)");
				choice = scanner.nextLine();
				flag = validateMoveInput(choice);
				if (!flag) {
					System.out.println("\nInvalid Input.\n");
				}
			} while (!flag);
		} else {
			do {
				System.out.println("Enter position: (A-2 B-2)");
				choice = scanner.nextLine();
				flag = validateMoveCInput(choice);
				if (!flag) {
					System.out.println("\nInvalid Input.\n");
				}
			} while (!flag);
		}
		return choice;
	}

//	/**
//	 * This method keep on taking input until user enter 2 or 3.
//	 * 
//	 * @return number of columns
//	 */
//	public int takeColumnInput() {
//		int number = 0;
//		do {
//			try {
//				System.out.print("Enter number of marbles to move in this direction (2 or 3): ");
//				number = scanner.nextInt();
//				scanner.nextLine();
//			} catch (InputMismatchException exception) {
//				System.out.println("Integers only, please.");
//				number = -1;
//				scanner.nextLine();
//			}
//			if (number != 2 && number != 3) {
//				System.out.println("\nInvalid Input.");
//				number = -1;
//			}
//		} while (number < 0);
//		return number;
//	}

	/**
	 * This method is used to take input from user. It keep on taking input until
	 * user enter move or movec
	 * 
	 * @return move or movec
	 */
	public String takeMoveInput() {
		String choice;
		boolean flag = false;
		do {
			if (flag) {
				System.out.println("\nInvalid Input.\n");
			}
			System.out.println("Enter Option:");
			System.out.println("Move single marble: Move");
			System.out.println("Move column: MoveC");
			choice = scanner.nextLine();
			choice = choice.toLowerCase();
			flag = true;
		} while (!choice.equals("move") && !choice.equals("movec"));
		return choice;
	}

	/**
	 * This method is used to take integer input from the user. The method will keep
	 * on taking input until the user input an integer.
	 * 
	 * @param scanner is used to take input
	 * @param message is used to display message according to take input.
	 * @return
	 */
	public static int takeIntegerInput(Scanner scanner, String message) {
		int number = 0;
		do {
			try {
				System.out.print(message);
				number = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException exception) {
				System.out.println("Integers only, please.");
				number = -1;
				scanner.nextLine();
			}
		} while (number < 0);
		return number;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public Turn getTurn() {
		return turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}

}