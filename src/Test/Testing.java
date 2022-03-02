package Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

import Game.Board;
import Game.BoardForTwo;
import Game.Game;
import Game.Player;
import Game.Turn;
import Pieces.Marble;

public class Testing {

	Game game = new Game();
	Scanner scanner = new Scanner(System.in);
	Board board = new BoardForTwo();

	@Test
	public void rowDifferenceTest() {
		int difference = game.rowDifference("A-1 B-2");
		assertEquals(-1, difference);
		difference = game.rowDifference("A-1 C-2");
		assertEquals(-2, difference);
		difference = game.rowDifference("B-1 B-2");
		assertEquals(0, difference);
		difference = game.rowDifference("B-1 A-2");
		assertEquals(1, difference);
	}

	@Test
	public void createNewMarbleTest() {
		Player player = new Player("Black");
		Marble marble = game.createNewMarble(player);
		assertEquals("B", marble.getSymbol());
		assertEquals("Black", marble.getColor());
		player = new Player("Red");
		marble = game.createNewMarble(player);
		assertEquals("R", marble.getSymbol());
		assertEquals("Red", marble.getColor());
	}

	@Test
	public void getRowIndexTest() {

		assertEquals(0, game.getRowIndex("A-1"));
		assertEquals(1, game.getRowIndex("B-1"));
		assertEquals(2, game.getRowIndex("C-7"));
		assertEquals(3, game.getRowIndex("D-1"));
		assertEquals(4, game.getRowIndex("E-3"));
		assertEquals(5, game.getRowIndex("F-1"));
		assertEquals(6, game.getRowIndex("G-5"));
		assertEquals(7, game.getRowIndex("H-1"));
		assertEquals(8, game.getRowIndex("I-1"));

	}

	@Test
	public void getColumnIndexTest() {
		assertEquals(2, game.getColumnIndex("A-1"));
		assertEquals(6, game.getColumnIndex("A-5"));
		assertEquals(-1, game.getColumnIndex("A-6"));
		assertEquals(1, game.getColumnIndex("C-1"));
		assertEquals(7, game.getColumnIndex("C-7"));
		assertEquals(-1, game.getColumnIndex("C-8"));
	}

	@Test
	public void validatePositionTest() {
		assertTrue(game.validatePosition("A", 5));
		assertTrue(game.validatePosition("A", 1));
		assertFalse(game.validatePosition("A", 6));
		assertTrue(game.validatePosition("B", 6));
		assertFalse(game.validatePosition("B", 0));
	}

	@Test
	public void currentTurnAndGetNextTurnTest() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("Black"));
		players.add(new Player("White"));
		players.add(new Player("Red"));
		Turn turn = new Turn(players);

		assertEquals(0, turn.currentTurn());
		turn.nextTurn();
		assertEquals(1, turn.currentTurn());
		turn.nextTurn();
		assertEquals(2, turn.currentTurn());
		turn.nextTurn();
		assertEquals(0, turn.currentTurn());
	}

	@Test
	public void validPositionTest() {

		assertTrue(board.isValidPosition(0, 4));
		assertFalse(board.isValidPosition(0, 1));
		assertFalse(board.isValidPosition(1, 8));
		assertTrue(board.isValidPosition(1, 7));
		assertTrue(board.isValidPosition(2, 4));
		assertTrue(board.isValidPosition(3, 5));
		assertTrue(board.isValidPosition(4, 8));
		assertTrue(board.isValidPosition(4, 1));
		assertTrue(board.isValidPosition(7, 4));

	}

	@Test
	public void setupTest() {
		game.setup();
		assertNotEquals(0, game.getPlayers().size());
		assertEquals(0, game.getTurn().currentTurn());
	}

}