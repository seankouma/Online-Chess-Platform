package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends ChessPiece {
	public Pawn(ChessBoard board, Color color) {
		super(board, color);
	}

	public String toString() {
		return this.getColor().equals(ChessPiece.Color.BLACK) ? "\u265F" : "\u2659";
	}
	
	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<String>();
		System.out.println("Current indices: " + this.row + " " + column);
		int[] fromIndices = {this.row, column};
		for (int row = 0; row < this.board.getBoardSize(); row++) {
			for (int col = 0; col < this.board.getBoardSize(); col++) {
				int[] toIndices = {row, col};
				int moveX = toIndices[1] - fromIndices[1];
				int moveY = toIndices[0] - fromIndices[0];
				
				
				int validDirection = this.getColor().equals(ChessPiece.Color.WHITE) ? -1 : 1;
				boolean initialPosition = (this.getColor().equals(ChessPiece.Color.WHITE) && fromIndices[0] == 9) || (this.getColor().equals(ChessPiece.Color.BLACK) && fromIndices[0] == 2);

				try {
					if (initialPosition && Math.abs(moveY) == 2 && moveX == 0 && board.getPiece(ChessBoard.indicesToString(toIndices)) == null && board.getPiece(ChessBoard.indicesToString(new int[] {fromIndices[0]+validDirection, fromIndices[1]})) == null) {
						moves.add(ChessBoard.indicesToString(toIndices));
					} else {
						if (moveX == 0 && moveY == validDirection && board.getPiece(ChessBoard.indicesToString(toIndices)) == null) {
							moves.add(ChessBoard.indicesToString(toIndices));
						} else {
							int[] leftDiagIndices = this.getLeftIndex(fromIndices, validDirection);
							int[] rightDiagIndices = this.getRightIndex(fromIndices, validDirection);
							if (leftDiagIndices != null && board.getPiece(ChessBoard.indicesToString(leftDiagIndices)) != null) {
								if (Arrays.equals(toIndices, leftDiagIndices)) moves.add(ChessBoard.indicesToString(toIndices));
							} if (rightDiagIndices != null && board.getPiece(ChessBoard.indicesToString(rightDiagIndices)) != null) {
								if (Arrays.equals(toIndices, rightDiagIndices)) moves.add(ChessBoard.indicesToString(toIndices));
							}
						}
					} 
				} catch (IllegalPositionException ex) {
					// Swallow exception since we can't print it
				}
			}
		}
		System.out.print("Moves: ");
		for (String move : moves) {
			System.out.print(move + " ");
		}
		return moves;
	}
	
	private int[] getLeftIndex(int[] fromIndices, int validDirection) {
		if ((fromIndices[0] != 0) && (fromIndices[0] != 7) && (fromIndices[1] != 0)) {
			int[] leftDiagIndices = {fromIndices[0]+validDirection, fromIndices[1]-1};
			return leftDiagIndices;
		}
		return null;
	}
	
	private int[] getRightIndex(int[] fromIndices, int validDirection) {
		if ((fromIndices[0] != 0) && (fromIndices[0] != 7) && (fromIndices[1] != 7)) {
			int[] rightDiagIndices = {fromIndices[0]+validDirection, fromIndices[1]+1};
			return rightDiagIndices;
		}
		return null;
	}
}
