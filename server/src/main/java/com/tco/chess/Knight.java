package chess;

import java.util.ArrayList;

public class Knight extends ChessPiece {
	public Knight(ChessBoard board, Color color) {
		super(board, color);
	}

	public String toString() {
		return this.getColor().equals(ChessPiece.Color.BLACK) ? "\u265E" : "\u2658";
	}
	
	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<String>();
		int[] fromIndices = {this.row, this.column};
		for (int row = 0; row < this.board.getBoardSize(); row++) {
			for (int col = 0; col < this.board.getBoardSize(); col++) {
				int[] toIndices = {row, col};
				int moveX = toIndices[1] - fromIndices[1];
				int moveY = toIndices[0] - fromIndices[0];
				if ((Math.abs(moveX) == 1 && Math.abs(moveY) == 2) || (Math.abs(moveX) == 2 && Math.abs(moveY) == 1)) {
					moves.add(ChessBoard.indicesToString(toIndices));
				}
				// TODO add same color piece check
			}
		}
		return moves;
	}
}
