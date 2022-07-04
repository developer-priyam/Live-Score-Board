package com.game.scoreboard.util;

import com.game.scoreboard.model.Game;

import java.util.List;

public class SummaryGenerator {

    private SummaryGenerator() {}

    public static String generateSummaryInRequiredFormat(Game game) {
        return game.gameDetails().homeTeam() +
                " " +
                game.gameDetails().homeTeamScore() +
                " - " +
                game.gameDetails().awayTeam() +
                " " +
                game.gameDetails().awayTeamScore();
    }

    public static List<Game> sortByTotalScore(List<Game> games) {
        games.sort((g1, g2) -> {
            int g1ts = g1.gameDetails().homeTeamScore() + g1.gameDetails().awayTeamScore();
            int g2ts = g2.gameDetails().homeTeamScore() + g2.gameDetails().awayTeamScore();
            return g1ts - g2ts;
        });
        return games;
    }
}
