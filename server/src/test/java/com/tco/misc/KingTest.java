package chess;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KingTest {

	ChessBoard board;
	
	@BeforeEach
	void beforeEach() {
		board = new ChessBoard();
		board.initialize();
	}
	
	@Test
	void testLegalMoves() throws IllegalPositionException {
		ChessPiece king = board.getPiece("f0");
		ArrayList<String> moves = king.legalMoves();
		assert(moves.size() >= 0);
	}
	
	@Test
	void testLegalMoves2() throws IllegalPositionException {
		ChessPiece king = board.getPiece("f9");
		ArrayList<String> moves = king.legalMoves();
		assert(moves.size() >= 0);
	}
	
	@Test
	void testLegalMoves3() throws IllegalPositionException {
		ChessPiece king = board.getPiece("f0");
		king.setPosition("f2");
		ArrayList<String> moves = king.legalMoves();
		assert(moves.size() >= 5);
	}
	
	@Test
	void testLegalMoves4() throws IllegalPositionException {
		ChessPiece king = board.getPiece("f0");
		king.setPosition("d6");
		ArrayList<String> moves = king.legalMoves();
		assert(moves.size() >= 8);
	}
	
	@Test
	void testLegalMoves5() throws IllegalPositionException {
		ChessPiece king = board.getPiece("f9");
		king.setPosition("e3");
		ArrayList<String> moves = king.legalMoves();
		assert(moves.size() >= 8);
	}
	
	@Test
	void testConstructor() {
		assert(new King(board, ChessPiece.Color.BLACK) instanceof King);
	}
	
	@Test
	void testToStringBlack() throws IllegalPositionException {
		ChessPiece king = board.getPiece("f9");
		String test = king.toString();
		assert(test.equals("\u265A"));
	}
	
	@Test
	void testToStringWhite() throws IllegalPositionException {
		ChessPiece king = board.getPiece("f0");
		String test = king.toString();
		assert(test.equals("\u2654"));
	}
	
	@Test
	void getColor() throws IllegalPositionException {
		ChessPiece king = board.getPiece("f0");
		assert(king.getColor().equals(ChessPiece.Color.WHITE));
	}
	
	@Test
	void getColor2() throws IllegalPositionException {
		ChessPiece king = board.getPiece("f9");
		assert(king.getColor().equals(ChessPiece.Color.BLACK));
	}
}
