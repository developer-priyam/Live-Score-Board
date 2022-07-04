package com.game.scoreboard.util;

import com.game.scoreboard.model.Game;

import java.util.List;

/**
 * This is the Helper class for Generating the Summary output representation for all the finished games.
 */
public class SummaryGenerator {

    private SummaryGenerator() {}

    // Gives string based output
    public static String generateSummaryInRequiredFormat(Game game) {
        return game.gameDetails().homeTeam() +
                " " +
                game.gameDetails().homeTeamScore() +
                " - " +
                game.gameDetails().awayTeam() +
                " " +
                game.gameDetails().awayTeamScore();
    }

    // Perform sorting based on the Total Score of the match.
    public static List<Game> sortByTotalScore(List<Game> games) {
        games.sort((g1, g2) -> {
            int g1ts = g1.gameDetails().homeTeamScore() + g1.gameDetails().awayTeamScore();
            int g2ts = g2.gameDetails().homeTeamScore() + g2.gameDetails().awayTeamScore();
            return g1ts - g2ts;
        });
        return games;
    }
}
