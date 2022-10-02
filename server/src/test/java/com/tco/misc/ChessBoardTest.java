package chess;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

	ChessBoard board;
	
	@BeforeEach
	void beforeEach() {
		board = new ChessBoard();
	}
	
	@Test
	void testConstructor() {
		assertNotNull(board);
	}
	
	@Test
	void testInitialize() {
		board.initialize();
		try {
			assert(board.getPiece("b0") instanceof Rook && board.getPiece("b0").getColor().equals(ChessPiece.Color.WHITE));
			assert(board.getPiece("c0") instanceof Knight && board.getPiece("c0").getColor().equals(ChessPiece.Color.WHITE));
			assert(board.getPiece("d0") instanceof Bishop && board.getPiece("d0").getColor().equals(ChessPiece.Color.WHITE));
			assert(board.getPiece("e0") instanceof Queen && board.getPiece("e0").getColor().equals(ChessPiece.Color.WHITE));
			assert(board.getPiece("f0") instanceof King && board.getPiece("f0").getColor().equals(ChessPiece.Color.WHITE));
			assert(board.getPiece("g0") instanceof Bishop && board.getPiece("g0").getColor().equals(ChessPiece.Color.WHITE));
			assert(board.getPiece("h0") instanceof Knight && board.getPiece("h0").getColor().equals(ChessPiece.Color.WHITE));
			assert(board.getPiece("i0") instanceof Rook && board.getPiece("i0").getColor().equals(ChessPiece.Color.WHITE));
			assert(board.getPiece("i9") instanceof Rook && board.getPiece("i8").getColor().equals(ChessPiece.Color.BLACK));
			assert(board.getPiece("h9") instanceof Knight && board.getPiece("i8").getColor().equals(ChessPiece.Color.BLACK));
			assert(board.getPiece("g9") instanceof Bishop && board.getPiece("i8").getColor().equals(ChessPiece.Color.BLACK));
			assert(board.getPiece("e9") instanceof Queen && board.getPiece("e9").getColor().equals(ChessPiece.Color.BLACK));
			assert(board.getPiece("f9") instanceof King && board.getPiece("f9").getColor().equals(ChessPiece.Color.BLACK));
			assert(board.getPiece("d9") instanceof Bishop && board.getPiece("d9").getColor().equals(ChessPiece.Color.BLACK));
			assert(board.getPiece("c9") instanceof Knight && board.getPiece("c9").getColor().equals(ChessPiece.Color.BLACK));
			assert(board.getPiece("b9") instanceof Rook && board.getPiece("b9").getColor().equals(ChessPiece.Color.BLACK));
			String[] positions = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
			for (String pos : positions) {
				String positionWhitePawn = pos + "1";
				String positionBlackPawn = pos + "8";
				assert(board.getPiece(positionWhitePawn) instanceof Pawn && board.getPiece(positionWhitePawn).getColor().equals(ChessPiece.Color.WHITE));
				assert(board.getPiece(positionBlackPawn) instanceof Pawn && board.getPiece(positionBlackPawn).getColor().equals(ChessPiece.Color.BLACK));
			}
		} catch (IllegalPositionException e) {
			fail("Exception");
		}
	}
	
	@Test
	void testPlacePiece() {
		Rook rook = new Rook(board, ChessPiece.Color.WHITE);
		Boolean placeSuccess = board.placePiece(rook, "c0");
		assert(placeSuccess);
	}
	
	@Test
	void testPlaceDuplicatePiece() {
		board.initialize();
		Rook rook = new Rook(board, ChessPiece.Color.WHITE);
		Boolean placeSuccess = board.placePiece(rook, "c0");
		assert(!placeSuccess);
	}
	
	@Test
	void testPlaceInvalidPiece() {
		Pawn pawn = new Pawn(board, ChessPiece.Color.BLACK);
		Boolean placeSuccess = board.placePiece(pawn, "z9");
		assert(!placeSuccess);
	}
	
	@Test
	void testPlaceOpponentPiece() {
		Rook whiteRook = new Rook(board, ChessPiece.Color.WHITE);
		Boolean placeSuccess = board.placePiece(whiteRook, "d0");
		assert(placeSuccess);
		Rook blackRook = new Rook(board, ChessPiece.Color.BLACK);
		placeSuccess = board.placePiece(blackRook, "d0");
		assert(placeSuccess);
	}
	
	@Test
	void testGetAndPlacePiece() throws IllegalPositionException {
		Bishop bish = new Bishop(board, ChessPiece.Color.WHITE);
		Boolean placeSuccess = board.placePiece(bish, "a3");
		assert(placeSuccess);
		ChessPiece bish2 = board.getPiece("a3");
		assert(bish.equals(bish2));
	}
	
	@Test
	void testGetCornerPiece() throws IllegalPositionException {
		board.initialize();
		ChessPiece rook = board.getPiece("i9");
		assert(rook instanceof Rook);
	}
	
	@Test
	void testGetNullPiece() throws IllegalPositionException {
		ChessPiece piece = board.getPiece("b0");
		assertNull(piece);
	}
	
	@Test
	void testMovePawn() throws IllegalMoveException, IllegalPositionException {
		board.initialize();
		board.move("c1", "c2", ChessPiece.Color.WHITE);
		assert(board.getPiece("c1") == null);
		assert(board.getPiece("c2") != null);
	}
	
	@Test
	void testMovePawn2() throws IllegalMoveException, IllegalPositionException {
		board.initialize();
		board.move("a1", "a3", ChessPiece.Color.WHITE);
		assert(board.getPiece("a2") == null);
		assert(board.getPiece("a3") != null);
	}
	
	@Test
	void testMoveRook() throws IllegalMoveException, IllegalPositionException {
		board.initialize();
		Boolean success = board.placePiece(board.getPiece("b0"), "b3");
		assert(success);
		board.move("b3", "b6", ChessPiece.Color.WHITE);
		assert(board.getPiece("b3") == null);
		assert(board.getPiece("b6") != null);
	}
	
	@Test
	void testMoveBishop() throws IllegalMoveException, IllegalPositionException {
		board.initialize();
		Boolean success = board.placePiece(board.getPiece("d0"), "d3");
		assert(success);
		board.move("d3", "e4", ChessPiece.Color.WHITE);
		assert(board.getPiece("d3") == null);
		assert(board.getPiece("e4") instanceof Bishop);
	}
	
	@Test
	void testIllegalPositionException() {
		board.initialize();
		Throwable exception = assertThrows(IllegalPositionException.class,
	            ()->{board.getPiece("z9");} );
	}
	
	@Test
	void testIllegalPositionException1() {
		board.initialize();
		Throwable exception = assertThrows(IllegalPositionException.class,
	            ()->{board.getPiece("z8");} );
	}
	
	@Test
	void testIllegalPositionException2() {
		board.initialize();
		Throwable exception = assertThrows(IllegalPositionException.class,
	            ()->{board.getPiece("z1");} );
	}
	
	@Test
	void testIllegalPositionException3() {
		board.initialize();
		Throwable exception = assertThrows(IllegalPositionException.class,
	            ()->{board.getPiece("c");} );
	}
	
	@Test
	void testIllegalPositionException4() {
		board.initialize();
		Throwable exception = assertThrows(IllegalPositionException.class,
	            ()->{board.getPiece("3");} );
	}

}
