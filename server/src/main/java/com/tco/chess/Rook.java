package chess;

import java.util.ArrayList;

public class Rook extends ChessPiece {

	public Rook(ChessBoard board, Color color) {
		super(board, color);
	}

	public String toString() {
		return this.getColor().equals(ChessPiece.Color.BLACK) ? "\u265C" : "\u2656";
	}
	
	public ArrayList<String> legalMoves() {
		return legalRookMoves(this.row, this.column, this.board, this.getColor());
	}

	public static ArrayList<String> legalRookMoves(int currentRow, int currentColumn, ChessBoard board, ChessPiece.Color color) {
		ArrayList<String> moves = new ArrayList<String>();
		int[] fromIndices = {currentRow, currentColumn};
		for (int row = 0; row < board.getBoardSize(); row++) {
			for (int col = 0; col < board.getBoardSize(); col++) {
				int[] toIndices = {row, col};
				int moveX = toIndices[1] - fromIndices[1];
				int moveY = toIndices[0] - fromIndices[0];
				boolean sameRow = fromIndices[0] == toIndices[0];
				boolean sameCol = fromIndices[1] == toIndices[1];
				if (sameRow ^ sameCol) {
					if (clearLoS(fromIndices, toIndices, moveX, moveY, board)) {
						String position = ChessBoard.indicesToString(new int[] {currentRow + moveY, currentColumn + moveX});
						ChessPiece pieceOnBoard;
						try {
							pieceOnBoard = board.getPiece(position);
							if (pieceOnBoard != null && pieceOnBoard.getColor().equals(color)) {
								continue;
							}
							moves.add(position);
						} catch (IllegalPositionException e) {
							// Swallow exception
						}
					}
				}
			}
		}
		return moves;
	}
	
	public static boolean clearLoS(int[] fromIndices, int[] toIndices, int moveX, int moveY, ChessBoard board) {
		int xChange = moveX > 0 ? 1 : -1;
		int yChange = moveY > 0 ? 1 : -1;
		xChange = moveX == 0 ? 0 : xChange;
		yChange = moveY == 0 ? 0 : yChange;
		int distance = moveX != 0 ? Math.abs(moveX) : Math.abs(moveY);
		for (int i = 1; i < distance; i++) {
			try {
				ChessPiece current = board.getPiece(ChessBoard.indicesToString(new int[] {fromIndices[0]+yChange*i, fromIndices[1]+xChange*i}));
				if (current != null) {
					return false;
				}
			} catch (IllegalPositionException e) {
				// Swallow exception
			}
		}
		return true;
	}
}
