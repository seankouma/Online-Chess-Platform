package chess;

import java.util.ArrayList;

public class Bishop extends ChessPiece {
	public Bishop(ChessBoard board, Color color) {
		super(board, color);
	}

	public String toString() {
		return this.getColor().equals(ChessPiece.Color.BLACK) ? "\u265D" : "\u2657";
	}
	
	public ArrayList<String> legalMoves() {
		return legalBishopMoves(this.row, this.column, this.board, this.getColor());
	}

	public static ArrayList<String> legalBishopMoves(int currentRow, int currentColumn, ChessBoard board, ChessPiece.Color color) {
		ArrayList<String> moves = new ArrayList<String>();
		System.out.println("Current indices: " + currentRow + " " + currentColumn);
		int[] fromIndices = {currentRow, currentColumn};
		for (int row = 0; row < board.getBoardSize(); row++) {
			for (int col = 0; col < board.getBoardSize(); col++) {
				int[] toIndices = {row, col};
				int moveX =  toIndices[1] - fromIndices[1];
				int moveY =  toIndices[0] - fromIndices[0];
				if (Math.abs(moveX) == Math.abs(moveY) && Math.abs(moveX) > 0) {
					if (clearLoS(fromIndices, toIndices, moveX, moveY, board)) {
						String position = ChessBoard.indicesToString(new int[] {currentRow + moveY, currentColumn + moveX});
						try {
							ChessPiece pieceOnBoard = board.getPiece(position);
							if (pieceOnBoard != null && pieceOnBoard.getColor().equals(color)) {
								continue;
							}
							moves.add(position);
						} catch (IllegalPositionException e) {
							// Ignore exception since we can't print it and it shouldn't ever happen
						}
					}
				}
			}
		}
		System.out.print("Moves: ");
		for (String move : moves) {
			System.out.print(move + " ");
		}
		return moves;
	}
	
	public static boolean clearLoS(int[] fromIndices, int[] toIndices, int moveX, int moveY, ChessBoard board) {
		int xChange = moveX > 0 ? 1 : -1;
		int yChange = moveY > 0 ? 1 : -1;
		for (int i = 1; i < Math.abs(moveX); i++) {
			try {
				ChessPiece current = board.getPiece(ChessBoard.indicesToString(new int[] {fromIndices[0]+yChange*i, fromIndices[1]+xChange*i}));
				if (current != null) {
					return false;
				}
			} catch (IllegalPositionException e) {
				// Ignore exception since we can't print it and it shouldn't ever happen
			}
		}
		return true;
	}
}
