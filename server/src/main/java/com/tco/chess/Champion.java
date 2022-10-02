package chess;

import java.util.ArrayList;

public class Champion extends ChessPiece {
	public Champion(ChessBoard board, Color color) {
		super(board, color);
	}
	
	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<String>();
		int[] fromIndices = {this.row, this.column};
		for (int row = 0; row < this.board.getBoardSize(); row++) {
			for (int col = 0; col < this.board.getBoardSize(); col++) {
				int[] toIndices = {row, col};
				int moveX = toIndices[1] - fromIndices[1];
				int moveY = toIndices[0] - fromIndices[0];
				if (isValidChampionMove(moveX, moveY) && !isTeamPiece(toIndices)) {
                    moves.add(ChessBoard.indicesToString(toIndices));
                }
			}
		}
		return moves;
	}

    private boolean isValidChampionMove(int moveX, int moveY) {
        if ((Math.abs(moveX) < 3 && moveY == 0) ||
            (Math.abs(moveY) < 3 && moveX == 0) ||
            (Math.abs(moveY) == 2 && Math.abs(moveX) == 2)) {
                return moveX != 0 || moveY != 0;
            }
            return false;
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
