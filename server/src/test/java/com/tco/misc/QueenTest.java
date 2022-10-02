package chess;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueenTest {

ChessBoard board;
	
	@BeforeEach
	void beforeEach() {
		board = new ChessBoard();
		board.initialize();
	}
	
	@Test
	void testGetColor() throws IllegalPositionException {
		ChessPiece queen = board.getPiece("d1");
		assert(queen.getColor().equals(ChessPiece.Color.WHITE));
	}
	
	@Test
	void testConstructor() {
		assertNotNull(new Queen(board, ChessPiece.Color.BLACK));
		assert((new Queen(board, ChessPiece.Color.BLACK).getColor().equals(ChessPiece.Color.BLACK)));
	}
	
	@Test
	void testGetAndSetPosition() throws IllegalPositionException {
		Queen queen = new Queen(board, ChessPiece.Color.BLACK);
		queen.setPosition("a5");
		assert(queen.getPosition().equals("a5"));
	}
	
	@Test
	void testToString() {
		Queen queen = new Queen(board,ChessPiece.Color.WHITE);
		assert(queen.toString().equals("\u2655"));
		Queen queen2 = new Queen(board, ChessPiece.Color.BLACK);
		assert(queen2.toString().equals("\u265B"));
	}
	
	@Test
	void testLegalMoves() {
		Queen queen = new Queen(board,ChessPiece.Color.WHITE);
		assert(queen.legalMoves().size() >= 0);
	}

}
