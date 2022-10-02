package chess;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ChessPiece {
	public enum Color {WHITE, BLACK}
	protected ChessBoard board;
	protected int row;
	protected int column;
	protected Color color;
	
	public ChessPiece(ChessBoard board, Color color) {
		this.board = board;
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public String getPosition() {
		char letter = (char) (this.column + 96);
		char number = (char) (this.row + 48);
		return "" + letter + number;
	}
	
	public void setPosition(String position) throws IllegalPositionException {
		int[] indices = ChessBoard.getIndices(position);
		this.row = indices[0];
		this.column = indices[1];
	}
	
	private boolean validPosition(String position) throws IllegalPositionException{
		Pattern pattern = Pattern.compile("^[a-l]\\d?\\d$");
		Matcher matcher = pattern.matcher(position);
		boolean matchFound = matcher.find();
		if (matchFound) {
			return true;
		} else {
			throw new IllegalPositionException();
		}
	}
	
	abstract public ArrayList<String> legalMoves();
}
