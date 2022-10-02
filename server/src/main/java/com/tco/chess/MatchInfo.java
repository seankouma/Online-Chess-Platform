package chess;

public class MatchInfo {
    String opponent;
    String gameState;
    int matchID;

    MatchInfo(String opponent, String gameState, int matchID) {
        this.opponent = opponent;
        this.gameState = gameState; 
        this.matchID = matchID;
    }
}
