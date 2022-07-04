package com.game.scoreboard.handler;

import com.game.scoreboard.model.Game;
import com.game.scoreboard.model.GameDetails;

import java.util.List;
import java.util.UUID;

public interface GameHandler {
    Game startGame(UUID eventId, GameDetails gameDetails);

    void finishGame(UUID eventId, UUID gameId);

    void updateGameScore(UUID eventId, UUID gameId, int homeTeamScore, int awayTeamScore);

    List<String> getSummaryByTotalScore(UUID eventId);
}
