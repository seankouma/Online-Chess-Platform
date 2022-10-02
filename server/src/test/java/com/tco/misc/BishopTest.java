package chess;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BishopTest {

	ChessBoard board;
	
	@BeforeEach
	void beforeEach() {
		board = new ChessBoard();
		board.initialize();
	}
	
	@Test
	void testLegalMoves() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("d0");
		ArrayList<String> moves = bish.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves2() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("g0");
		ArrayList<String> moves = bish.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves3() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("d0");
		bish.setPosition("e3");
		ArrayList<String> moves = bish.legalMoves();
		assert(moves.size() >= 7);
		assert(moves.contains("f4"));
		assert(moves.contains("d4"));
		assert(moves.contains("g5"));
		assert(moves.contains("c5"));
		assert(moves.contains("h6"));
		assert(moves.contains("b6"));
		assert(moves.contains("a7"));
	}
	
	@Test
	void testLegalMoves4() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("d9");
		board.placePiece(bish, "a3");
		ArrayList<String> moves = bish.legalMoves();
		assert(moves.contains("b4"));
		assert(moves.contains("c5"));
		assert(moves.contains("d6"));
		assert(moves.contains("e7"));
	}
	
	@Test
	void testLegalMoves5() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("d9");
		ArrayList<String> moves = bish.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves6() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("g9");
		ArrayList<String> moves = bish.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves7() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("d0");
		board.placePiece(bish, "a5");
		ArrayList<String> moves = bish.legalMoves();
		assert(moves.size() >= 4);
	}
	
	@Test
	void testLegalMoves8() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("d0");
		board.placePiece(bish, "d4");
		ArrayList<String> moves = bish.legalMoves();
		assert(moves.size() >= 8);
		assert(moves.contains("c3"));
		assert(moves.contains("c5"));
		assert(moves.contains("e3"));
		assert(moves.contains("e5"));
		assert(moves.contains("f6"));
		assert(moves.contains("f2"));
		assert(moves.contains("b6"));
		assert(moves.contains("b2"));
	}
	
	@Test
	void testToStringBlack() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("d9");
		String test = bish.toString();
		assert(test.equals("\u265D"));
	}
	
	@Test
	void testToStringWhite() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("d0");
		String test = bish.toString();
		assert(test.equals("\u2657"));
	}
	
	@Test
	void testConstructor() {
		Bishop bish = new Bishop(board, ChessPiece.Color.BLACK);
		assert(bish.getColor().equals(ChessPiece.Color.BLACK));
		assert(bish.board.equals(board));
	}
	
	@Test
	void testConstructor2() {
		Bishop bish = new Bishop(board, ChessPiece.Color.WHITE);
		assert(bish.getColor().equals(ChessPiece.Color.WHITE));
		assert(bish.board.equals(board));
	}

}
