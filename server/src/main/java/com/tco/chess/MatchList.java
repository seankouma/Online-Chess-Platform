package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class MatchList {
    private static ArrayList<Match> matches = new ArrayList<Match>();

    public static Match getMatch(int index) {
        if (matches.size() == 0) {
            makeMatch("test@gmail.com", "test1@gmail.com");
        }
        if (index >= 0 && index < matches.size()){
            return matches.get(index);
        }
        System.out.println("Match not created");
        return null;
    }

    public static void addMatch(Match match) {
        matches.add(match);
    }

    public static ChessPiece.Color getColor(int index, String username) {
        if (index >= 0 && index < matches.size()){
            return matches.get(index).getColor(username);
        }
        System.out.println("Match not created");
        return null;
    } 

    public static void makeMatch(String usernameWhite, String usernameBlack) {
        Match newMatch = new Match(usernameWhite, usernameBlack, new ChessBoard());
        matches.add(newMatch);
    }

    public static int getLatestMatchID() {
        return matches.size() - 1;
    }

    public static ArrayList<MatchInfo> getMatchInfo(String username) {
        ArrayList<MatchInfo> info = new ArrayList<MatchInfo>();
        for (int i = 0; i < matches.size(); i++) {
            String opponent = matches.get(i).getOpponent(username);
            if (opponent != null) {
                info.add(new MatchInfo(opponent, "In Progress", i));
            }
        }
        return info;
    }

}
