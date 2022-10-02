package chess;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

public class ChessBoard {
	private ChessPiece[][] board;
	
	ChessBoard() {
		this.board = new ChessPiece[12][12];
	}
	
	public void initialize() {
		HashMap<Class<? extends ChessPiece>, String[]> pieces = new HashMap<Class<? extends ChessPiece>, String[]>();
		pieces.put(Rook.class, new String[] {"b0", "i0", "b9", "i9"});
		pieces.put(Knight.class, new String[] {"c0", "h0", "c9", "h9"});
		pieces.put(Bishop.class, new String[] {"d0", "g0", "d9", "g9"});
		pieces.put(Queen.class, new String[] {"e0", "e9"});
		pieces.put(King.class, new String[] {"f0", "f9"});
		pieces.put(Pawn.class, new String[] {"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1", "j1", "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "i8", "j8"});
		pieces.put(Champion.class, new String[] {"a0", "j0", "a9", "j9"});
		pieces.put(Wizard.class, new String[] {"w1", "w2", "w3", "w4"});
		
		for (Class<? extends ChessPiece> piece : pieces.keySet()) {
			for (String position : pieces.get(piece)) {
				ChessPiece current;
				try {
					ChessPiece.Color color = Character.getNumericValue(position.charAt(1)) < 3 ? ChessPiece.Color.WHITE : ChessPiece.Color.BLACK;
					current = piece.getConstructor(ChessBoard.class, ChessPiece.Color.class).newInstance(this, color);
					this.placePiece(current, position);
				} catch (Exception e) {
					// Swallow any exceptions since we aren't allowed to print them
				}
			}
		}
	}
	
	public ChessPiece getPiece(String position) throws IllegalPositionException {
		if (position == null || position.length() != 2) {
			throw new IllegalPositionException();
		}
		int[] indices = getIndices(position);
		return this.board[indices[0]][indices[1]];
	}
	
	public boolean placePiece(ChessPiece piece, String position) {
		try {
			ChessPiece current = this.getPiece(position);
			if (current != null && current.getColor() == piece.color) return false;
			else {
				int[] indices = this.getIndices(position);
				this.board[indices[0]][indices[1]] = piece;
				piece.setPosition(position);
				return true;
			}
			
		} catch (IllegalPositionException ex) {
			return false;
		}
	}
	
	public boolean move(String fromPosition, String toPosition, ChessPiece.Color color) {
		try {
			System.out.println("Move from " + fromPosition + " to " + toPosition);
			ChessPiece piece = this.getPiece(fromPosition);
			if (this.isLegalMove(piece, fromPosition, toPosition) && piece.getColor().equals(color)) {
				int[] toIndices = this.getIndices(toPosition);
				this.board[toIndices[0]][toIndices[1]] = piece;
				int[] fromIndices = this.getIndices(fromPosition);
				this.board[fromIndices[0]][fromIndices[1]] = null;
				piece.setPosition(toPosition);
				return true;
			} else {
				return false;
			}
		} catch (IllegalPositionException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean isLegalMove(ChessPiece piece, String fromPosition, String toPosition) throws IllegalPositionException {
		if (piece.legalMoves().contains(toPosition)) 
			return true;
		return false;
	}

	public static int[] getIndices(String position) throws IllegalPositionException {
		if (position.equals("w1")) {
			return new int[] {11,0};
		} else if (position.equals("w2")) {
			return new int[] {11,11};
		} else if (position.equals("w3")) {
			return new int[] {0, 11};
		} else if (position.equals("w4")) {
			return new int[] {0,0};
		} else if (validPosition(position)) {
			int col = position.charAt(0) - 96;
			int row = 10 - Integer.parseInt(position.substring(1));
			return new int[] {row, col};
		} else {
			throw new IllegalPositionException();
		}
	}

	public static String indicesToString(int[] indices) {
		int[] upperLeft = {0,0};
		int[] upperRight = {0,11};
		int[] bottomLeft = {11,0};
		int[] bottomRight = {11,11};
		if (Arrays.equals(indices, upperLeft)) {
			return "w4";
		} else if (Arrays.equals(indices, upperRight)) {
			return "w3";
		} else if (Arrays.equals(indices, bottomLeft)) {
			return "w1";
		} else if (Arrays.equals(indices, bottomRight)) {
			return "w2";
		} else {
			char letter = (char) (indices[1] + 96);
			int number = 10 - indices[0];
			return "" + letter + Integer.toString(number);
		}
	}

	public static boolean validPosition(String position) throws IllegalPositionException{
		Pattern pattern = Pattern.compile("^[a-l][0-9][0-9]?$");
		Matcher matcher = pattern.matcher(position);
		boolean matchFound = matcher.find();
		if (matchFound) {
			return true;
		} else {
			throw new IllegalPositionException();
		}
	}

	public int getBoardSize() {
		return this.board.length;
	}

	public String[][] getBoardString() {
		String[][] boardString = new String[12][12];
		for (int i = 0; i < boardString.length; i++) {
			for (int j = 0; j < boardString[0].length; j++) {
				if (this.board[i][j] != null) {
					ChessPiece current = this.board[i][j];
					String currentColor = current.getColor().equals(ChessPiece.Color.WHITE) ? "white" : "black";
						boardString[i][j] = currentColor + current.getClass().getSimpleName();
				}
			}
		}
		return boardString;
	}

}
