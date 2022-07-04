package com.game.scoreboard.handler;

import com.game.scoreboard.exceptions.EventNotFoundException;
import com.game.scoreboard.exceptions.GameNotFoundException;
import com.game.scoreboard.exceptions.NoActiveGameFoundException;
import com.game.scoreboard.model.Game;
import com.game.scoreboard.model.GameDetails;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FootBallGameTest {

    private FootBallGame footBallGame;

    private UUID eventId;

    private Game game;

    @BeforeAll
    public void setup() {
        footBallGame = new FootBallGame();
        eventId = footBallGame.createNewEvent();
        GameDetails gameDetails = new GameDetails(0, 0, "Spain", "Italy");
        game = footBallGame.startGame(eventId, gameDetails);
    }

    @Test
    @Order(1)
    void startGameTest() {
        Game gameFroStore = footBallGame.getAllGamesInStoreForTheEvent(eventId)
                                        .get("active")
                                        .stream()
                                        .filter(g -> g.id().equals(game.id()))
                                        .findFirst()
                                        .orElse(null);
        assert gameFroStore != null;
        assertEquals(game.gameDetails().awayTeam(), gameFroStore.gameDetails().awayTeam());
        assertEquals(0, gameFroStore.gameDetails().awayTeamScore());
        assertEquals(0, gameFroStore.gameDetails().homeTeamScore());
    }

    @Test
    @Order(2)
    void updateScoreTest() {
        footBallGame.updateGameScore(eventId, game.id(), 2, 3);
        Game gameFroStore = footBallGame.getAllGamesInStoreForTheEvent(eventId)
                .get("active")
                .stream()
                .filter(g -> g.id().equals(game.id()))
                .findFirst()
                .orElse(null);
        assert gameFroStore != null;
        assertEquals(2, gameFroStore.gameDetails().homeTeamScore());
        assertEquals(3, gameFroStore.gameDetails().awayTeamScore());
    }

    @Test
    @Order(3)
    void finishGameTest() {
        footBallGame.updateGameScore(eventId, game.id(), 4, 6);
        footBallGame.finishGame(eventId, game.id());
        Map<String, List<Game>> eventGamesMap = footBallGame.getAllGamesInStoreForTheEvent(eventId);
        List<Game> finishedGames = eventGamesMap.get("finished");
        List<Game> activeGames = eventGamesMap.get("active");
        Game gameFroStore = finishedGames.stream()
                                         .filter(g -> g.id().equals(game.id()))
                                         .findFirst()
                                         .orElse(null);
        assert gameFroStore != null;
        assertEquals(0, activeGames.size());
        assertEquals(1, finishedGames.size());
        assertEquals(6, gameFroStore.gameDetails().awayTeamScore());
        assertEquals(4, gameFroStore.gameDetails().homeTeamScore());
        assertEquals("Italy", gameFroStore.gameDetails().awayTeam());
        assertEquals("Spain", gameFroStore.gameDetails().homeTeam());
    }

    @Test
    @Order(4)
    void eventSummaryTest() {
        TestHelper.addTestDataInStore(footBallGame, eventId);
        List<String> summaryList = footBallGame.getSummaryByTotalScore(eventId);
        for (String score : summaryList) {
            System.out.println(score);
        }

        assertEquals("Uruguay 6 - Italy 6", summaryList.get(0));
        assertEquals("Spain 10 - Brazil 2", summaryList.get(1));
        assertEquals("England 5 - Portugal 5", summaryList.get(2));
        assertEquals("Spain 4 - Italy 6", summaryList.get(3));
        assertEquals("Mexico 0 - Canada 5", summaryList.get(4));
        assertEquals("Argentina 3 - Australia 1", summaryList.get(5));
        assertEquals("Germany 2 - France 2", summaryList.get(6));
        assertEquals("Czech Republic 1 - Russia 2", summaryList.get(7));
    }

    @Test
    @Order(5)
    void eventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> footBallGame.startGame(UUID.randomUUID(), game.gameDetails()));
    }

    @Test
    @Order(6)
    void gameNotFoundException() {
        GameDetails gameDetails = new GameDetails(0, 0, "Spain", "Italy");
        Game game = footBallGame.startGame(eventId, gameDetails);
        footBallGame.finishGame(eventId, game.id());
        assertThrows(GameNotFoundException.class, () -> footBallGame.finishGame(eventId, game.id()));
    }

    @Test
    @Order(7)
    void noActiveGameFounDException() {
        GameDetails gameDetails = new GameDetails(0, 0, "Spain", "Italy");
        Game game = footBallGame.startGame(eventId, gameDetails);
        footBallGame.finishGame(eventId, game.id());
        assertThrows(NoActiveGameFoundException.class, () -> footBallGame.updateGameScore(eventId, game.id(), 4, 6));
    }

}
