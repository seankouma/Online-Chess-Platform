package chess;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnightTest {

	ChessBoard board;
	
	@BeforeEach
	void beforeEach() {
		board = new ChessBoard();
		board.initialize();
	}
	
	@Test
	void testGetColor() throws IllegalPositionException {
		ChessPiece knight = board.getPiece("b1");
		assert(knight.getColor().equals(ChessPiece.Color.WHITE));
	}
	
	@Test
	void testConstructor() {
		assertNotNull(new Knight(board, ChessPiece.Color.BLACK));
		assert((new Knight(board, ChessPiece.Color.BLACK).getColor().equals(ChessPiece.Color.BLACK)));
	}
	
	@Test
	void testGetAndSetPosition() throws IllegalPositionException {
		Knight knight = new Knight(board, ChessPiece.Color.BLACK);
		knight.setPosition("a5");
		assert(knight.getPosition().equals("a5"));
	}
	
	@Test
	void testToString() {
		Knight knight = new Knight(board,ChessPiece.Color.WHITE);
		assert(knight.toString().equals("\u2658"));
		Knight knight2 = new Knight(board, ChessPiece.Color.BLACK);
		assert(knight2.toString().equals("\u265E"));
	}
	
	@Test
	void testLegalMoves() {
		Knight knight = new Knight(board,ChessPiece.Color.WHITE);
		assert(knight.legalMoves().size() >= 0);
	}

}
