package chess;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PawnTest {

ChessBoard board;
	
	@BeforeEach
	void beforeEach() {
		board = new ChessBoard();
		board.initialize();
	}
	
	@Test
	void testLegalMoves() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("a1");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 2);
	}
	
	@Test
	void testLegalMoves2() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("h1");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 2);
	}
	
	@Test
	void testLegalMoves3() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("a8");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 2);
	}
	
	@Test
	void testLegalMoves4() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("h8");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 2);
	}
	
	@Test
	void testLegalMoves5() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("a1");
		pawn.setPosition("a3");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 1);
	}
	
	@Test
	void testLegalMoves6() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("h8");
		pawn.setPosition("h7");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 1);
	}
	
	@Test
	void testLegalMoves7() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("a1");
		pawn.setPosition("b7");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 2);
	}
	
	@Test
	void testLegalMoves8() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("h8");
		pawn.setPosition("f2");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 2);
	}
	
	@Test
	void testLegalMoves9() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("a1");
		pawn.setPosition("b9");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves10() throws IllegalPositionException {
		ChessPiece pawn = board.getPiece("h8");
		pawn.setPosition("h0");
		ArrayList<String> moves = pawn.legalMoves();
		assert(moves.size() == 0);
	}
	
	@Test
	void testLegalMoves11() throws IllegalPositionException {
		ChessPiece whitePawn = board.getPiece("a1");
		ArrayList<String> moves = whitePawn.legalMoves();
		assert(moves.size() >= 2);
	}
	
	@Test
	void testToStringWhite() {
		ChessPiece pawn = new Pawn(board, ChessPiece.Color.WHITE);
		assert(pawn.toString().equals("\u2659"));
	}
	
	@Test
	void testToStringBlack() {
		ChessPiece pawn = new Pawn(board, ChessPiece.Color.BLACK);
		assert(pawn.toString().equals("\u265F"));
	}
	
	@Test
	void testConstructor() {
		ChessPiece pawn = new Pawn(board, ChessPiece.Color.BLACK);
		assert(pawn.getColor().equals(ChessPiece.Color.BLACK));
		assert(pawn.board.equals(board));
	}
}
