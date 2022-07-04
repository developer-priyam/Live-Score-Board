package com.game.scoreboard.handler;

import com.game.scoreboard.exceptions.NoActiveGameFoundException;
import com.game.scoreboard.model.Game;
import com.game.scoreboard.model.GameDetails;
import com.game.scoreboard.util.GameOperations;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation class for the Game of type Football. Similar implementation classes can be created for every other type of game.
 *
 * This class uses the singleton instance of GameOperation Utility class to handle score board changes.
 */
public class FootBallGame implements GameHandler {

    private final GameOperations gameOperations;

    public FootBallGame() {
        this.gameOperations = GameOperations.getInstance();
    }

    public UUID createNewEvent() {
        return gameOperations.createNewEvent();
    }

    public Map<String, List<Game>> getAllGamesInStoreForTheEvent(UUID eventId) {
        return gameOperations.getAllGamesOfTheEvents(eventId);
    }


    @Override
    public Game startGame(UUID eventId, GameDetails gameDetails) {
        Game game = new Game(UUID.randomUUID(), gameDetails);
        gameOperations.startGame(eventId, game);
        return game;
    }

    @Override
    public void finishGame(UUID eventId, UUID gameId) {
        gameOperations.finishGame(eventId, gameId);
    }

    @Override
    public void updateGameScore(UUID eventId, UUID gameId, int homeTeamScore, int awayTeamScore) {
        List<Game> activeGames = getAllGamesInStoreForTheEvent(eventId).get("active");
        Game existingGame = activeGames.stream().filter(game -> game.id().equals(gameId)).findFirst().orElse(null);
        if (existingGame != null) {
            Game updatedGameScore = new Game(existingGame.id(),
                                             new GameDetails(homeTeamScore,
                                                             awayTeamScore,
                                                             existingGame.gameDetails().homeTeam(),
                                                             existingGame.gameDetails().awayTeam()));
            gameOperations.updateScore(eventId, existingGame, updatedGameScore);
        } else {
            throw new NoActiveGameFoundException("Requested Active Not Found. May be It is already Ended.");
        }
    }

    @Override
    public List<String> getSummaryByTotalScore(UUID eventId) {
        return gameOperations.getEventSummaryByTotalScore(eventId);
    }
}
