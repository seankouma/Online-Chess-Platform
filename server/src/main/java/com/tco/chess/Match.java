package chess;

public class Match {
    private String usernameWhite;
    private String usernameBlack;
    private ChessBoard board;
    private ChessPiece.Color turn = ChessPiece.Color.WHITE;

    public Match(String usernameWhite, String usernameBlack, ChessBoard board) {
        this.usernameWhite = usernameWhite;
        this.usernameBlack = usernameBlack;
        this.board = board;
        this.board.initialize();
    }
    
    public ChessPiece.Color getColor(String username) {
        return usernameWhite.equals(username) ? ChessPiece.Color.WHITE : ChessPiece.Color.BLACK;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public ChessPiece.Color getTurn() {
        return this.turn;
    }

    public void nextTurn() {
        this.turn = this.turn.equals(ChessPiece.Color.WHITE) ? ChessPiece.Color.BLACK : ChessPiece.Color.WHITE;
    }

    public String getOpponent(String username) {
        if (username.equals(this.usernameWhite)) {
            return this.usernameBlack;
        } else if (username.equals(this.usernameBlack)) {
            return this.usernameWhite;
        } else {
            return null;
        }
    }
}
