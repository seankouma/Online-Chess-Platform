package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends ChessPiece {
	public King(ChessBoard board, Color color) {
		super(board, color);
	}

	public String toString() {
		return this.getColor().equals(ChessPiece.Color.BLACK) ? "\u265A" : "\u2654";
	}
	
	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<String>();
		int[] fromIndices = {this.row, column};
		System.out.println("Current indices: " + this.row + " " + column);
		for (int row = 0; row < this.board.getBoardSize(); row++) {
			for (int col = 0; col < this.board.getBoardSize(); col++) {
				int[] toIndices = {row, col};
				int moveX =  toIndices[1] - fromIndices[1];
				int moveY =  toIndices[0] - fromIndices[0];
				if (Math.abs(moveX) <= 1 && Math.abs(moveY) <= 1 && !isTeamPiece(toIndices) && !Arrays.equals(fromIndices, toIndices)) {
					String position = ChessBoard.indicesToString(new int[] {this.row + moveY, this.column + moveX});
					moves.add(position);
				}
			}
		}
		System.out.print("Moves: ");
		for (String move : moves) {
			System.out.print(move + " ");
		}
		return moves;
	}
	
	private boolean isTeamPiece(int[] toIndices) {
		try {
			ChessPiece piece = board.getPiece(ChessBoard.indicesToString(toIndices));
			if (piece != null && piece.getColor().equals(color)) {
				return true;
			}
		} catch (IllegalPositionException e) {
			// Swallow exception since we can't print it and it shouldn't ever happen
		}
		return false;
	}
}
