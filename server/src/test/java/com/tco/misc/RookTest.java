package chess;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RookTest {

	ChessBoard board;
	
	@BeforeEach
	void beforeEach() {
		board = new ChessBoard();
		board.initialize();
	}
	
	@Test
	void testLegalMoves() throws IllegalPositionException {
		ChessPiece rook = board.getPiece("b0");
		ArrayList<String> moves = rook.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves2() throws IllegalPositionException {
		ChessPiece rook = board.getPiece("i9");
		ArrayList<String> moves = rook.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves3() throws IllegalPositionException {
		ChessPiece rook = board.getPiece("b0");
		rook.setPosition("e3");
		ArrayList<String> moves = rook.legalMoves();
		assert(moves.size() >= 11);
	}
	
	@Test
	void testLegalMoves4() throws IllegalPositionException {
		ChessPiece bish = board.getPiece("i9");
		bish.setPosition("h3");
		ArrayList<String> moves = bish.legalMoves();
		assert(moves.size() >= 11);
	}
	
	@Test
	void testLegalMoves5() throws IllegalPositionException {
		ChessPiece rook = board.getPiece("b9");
		ArrayList<String> moves = rook.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves6() throws IllegalPositionException {
		ChessPiece rook = board.getPiece("i9");
		ArrayList<String> moves = rook.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves7() throws IllegalPositionException {
		ChessPiece rook = board.getPiece("b0");
		rook.setPosition("a7");
		ArrayList<String> moves = rook.legalMoves();
		assert(moves.size() >= 6);
	}
	
	@Test
	void testLegalMoves8() throws IllegalPositionException {
		ChessPiece rook = board.getPiece("i9");
		rook.setPosition("h3");
		ArrayList<String> moves = rook.legalMoves();
		assert(moves.size() >= 11);
	}
	
	@Test
	void testToStringBlack() throws IllegalPositionException {
		ChessPiece rook = board.getPiece("i9");
		String test = rook.toString();
		assert(test.equals("\u265C"));
	}
	
	@Test
	void testToStringWhite() throws IllegalPositionException {
		ChessPiece rook = board.getPiece("b0");
		String test = rook.toString();
		assert(test.equals("\u2656"));
	}
	
	@Test
	void testConstructor() {
		Rook rook = new Rook(board, ChessPiece.Color.BLACK);
		assert(rook.getColor().equals(ChessPiece.Color.BLACK));
		assert(rook.board.equals(board));
	}
	
	@Test
	void testConstructor2() {
		Rook rook = new Rook(board, ChessPiece.Color.WHITE);
		assert(rook.getColor().equals(ChessPiece.Color.WHITE));
		assert(rook.board.equals(board));
	}
	
	@Test
	void testIllegalPositionException() {
		Rook rook = new Rook(board, ChessPiece.Color.WHITE);
		Throwable exception = assertThrows(IllegalPositionException.class,
	            ()->{rook.setPosition("z9");} );
	}

}
