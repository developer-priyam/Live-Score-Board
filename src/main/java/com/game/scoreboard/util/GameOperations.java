package com.game.scoreboard.util;

import com.game.scoreboard.exceptions.EventNotFoundException;
import com.game.scoreboard.exceptions.GameNotFoundException;
import com.game.scoreboard.model.Event;
import com.game.scoreboard.model.Game;
import com.game.scoreboard.store.InMemoryScoreboard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the layer between the in-memory database (or future actual DB) and handler / service layer.
 *
 * This also makes the handlers and DB independent of each other.
 *
 * This class implements Singleton Design Pattern.
 */
public class GameOperations {

    private final InMemoryScoreboard inMemoryScoreboard;

    private static GameOperations gameOperations;

    private GameOperations() {
        this.inMemoryScoreboard = InMemoryScoreboard.getInstance();
    }

    public static GameOperations getInstance() {
        if(gameOperations == null) {
            synchronized (GameOperations.class) {
                if (gameOperations == null) {
                    gameOperations = new GameOperations();
                }
            }
        }
        return gameOperations;
    }

    public UUID createNewEvent() {
        Event event = new Event(UUID.randomUUID(), Collections.emptyList(), Collections.emptyList());
        addOrUpdateEventDetailsInTheStore(event.id(), event);
        return event.id();
    }

    public Map<String, List<Game>> getAllGamesOfTheEvents(UUID eventId) {
        Event event = getEventDetailsFromStore(eventId);
        Map<String, List<Game>> eventGameMapByGameStatus = new HashMap<>();
        if (event != null) {
            eventGameMapByGameStatus.put("active", event.activeGames());
            eventGameMapByGameStatus.put("finished", event.finishedGames());
        } else {
            throw new EventNotFoundException("Invalid Event Id.");
        }
        return eventGameMapByGameStatus;
    }

    public void startGame(UUID eventId, Game game) {
        Event event = getEventDetailsFromStore(eventId);
        if (event != null) {
            List<Game> activeGames = (event.activeGames().isEmpty()) ? new ArrayList<>() : event.activeGames();
            activeGames.add(game);
            addOrUpdateEventDetailsInTheStore(eventId, new Event(eventId, activeGames, event.finishedGames()));
        } else {
            throw new EventNotFoundException("Invalid Event Id.");
        }
    }

    public void updateScore(UUID eventId, Game oldGame, Game newGame) {
        Event event = getEventDetailsFromStore(eventId);
        if (event != null) {
            List<Game> activeGames = event.activeGames().stream().filter(g -> !g.id().equals(oldGame.id())).collect(Collectors.toList());
            activeGames.add(newGame);
            addOrUpdateEventDetailsInTheStore(eventId, new Event(eventId, activeGames, event.finishedGames()));
        } else {
            throw new EventNotFoundException("Invalid Event id");
        }
    }

    public void finishGame(UUID eventId, UUID gameId) {
        Event event = getEventDetailsFromStore(eventId);
        if (event != null) {
            Game game = event.activeGames().stream().filter(g -> g.id().equals(gameId)).findFirst().orElse(null);
            if (game != null) {
                List<Game> activeGames = event.activeGames().stream().filter(g -> !g.id().equals(gameId)).toList();
                List<Game> finishedGames = (event.finishedGames().isEmpty()) ? new ArrayList<>() : event.finishedGames();
                finishedGames.add(game);
                addOrUpdateEventDetailsInTheStore(eventId, new Event(eventId, activeGames, finishedGames));
            } else {
                throw new GameNotFoundException("Invalid Game ID");
            }
        }
    }

    public List<String> getEventSummaryByTotalScore(UUID eventId) {
        List<String> summary = new ArrayList<>();
        Event event = getEventDetailsFromStore(eventId);
        if (event != null) {
            List<Game> finishedGames = SummaryGenerator.sortByTotalScore(event.finishedGames());
            for (Game game : finishedGames) {
                summary.add(SummaryGenerator.generateSummaryInRequiredFormat(game));
            }
        } else {
            throw new EventNotFoundException(" Invalid Event ID");
        }
        // Once we have the sorted result then we reverse the result. This way we get the most recent and highest (by total) scores on the top.
        Collections.reverse(summary);
        return summary;
    }

    private Event getEventDetailsFromStore(UUID eventId) { return inMemoryScoreboard.getEventMap().get(eventId); }

    private void addOrUpdateEventDetailsInTheStore(UUID eventId, Event event) { inMemoryScoreboard.setEventMap(eventId, event); }

}
