package chess;

import java.util.ArrayList;

public class Queen extends ChessPiece {
	public Queen(ChessBoard board, Color color) {
		super(board, color);
	}

	public String toString() {
		return this.getColor().equals(ChessPiece.Color.BLACK) ? "\u265B" : "\u2655";
	}
	
	public ArrayList<String> legalMoves() {
		ArrayList<String> legalQueenMoves = new ArrayList<String>();
		ArrayList<String> legalDiagMoves = Bishop.legalBishopMoves(this.row, this.column, this.board, this.getColor());
		ArrayList<String> legalRookMoves = Rook.legalRookMoves(this.row, this.column, this.board, this.getColor());
		legalQueenMoves.addAll(legalDiagMoves);
		legalQueenMoves.addAll(legalRookMoves);
		return legalQueenMoves;
	}
}
