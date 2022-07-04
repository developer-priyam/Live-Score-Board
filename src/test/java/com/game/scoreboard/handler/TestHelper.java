package com.game.scoreboard.handler;

import com.game.scoreboard.model.Game;
import com.game.scoreboard.model.GameDetails;

import java.util.UUID;

public class TestHelper {

    public static void addTestDataInStore(FootBallGame footBallGame, UUID eventId) {
        // Game 1
        GameDetails gameDetails1 = new GameDetails(0, 0, "Mexico", "Canada");
        Game game1 = footBallGame.startGame(eventId, gameDetails1);
        // Game 2
        GameDetails gameDetails2 = new GameDetails(0, 0, "Spain", "Brazil");
        Game game2 = footBallGame.startGame(eventId, gameDetails2);
        // Game 3
        GameDetails gameDetails3 = new GameDetails(0, 0, "Germany", "France");
        Game game3 = footBallGame.startGame(eventId, gameDetails3);
        // Game 4
        GameDetails gameDetails4 = new GameDetails(0, 0, "Uruguay", "Italy");
        Game game4 = footBallGame.startGame(eventId, gameDetails4);
        // Game 5
        GameDetails gameDetails5 = new GameDetails(0, 0, "Argentina", "Australia");
        Game game5 = footBallGame.startGame(eventId, gameDetails5);
        // Game 6
        GameDetails gameDetails6 = new GameDetails(0, 0, "England", "Portugal");
        Game game6 = footBallGame.startGame(eventId, gameDetails6);
        // Game 7
        GameDetails gameDetails7 = new GameDetails(0, 0, "Czech Republic", "Russia");
        Game game7 = footBallGame.startGame(eventId, gameDetails7);

        // Updating Score and Finishing Game
        footBallGame.updateGameScore(eventId, game1.id(), 0, 5);
        footBallGame.finishGame(eventId, game1.id());
        footBallGame.updateGameScore(eventId, game2.id(), 10, 2);
        footBallGame.finishGame(eventId, game2.id());
        footBallGame.updateGameScore(eventId, game3.id(), 2, 2);
        footBallGame.finishGame(eventId, game3.id());
        footBallGame.updateGameScore(eventId, game4.id(), 6, 6);
        footBallGame.finishGame(eventId, game4.id());
        footBallGame.updateGameScore(eventId, game5.id(), 3, 1);
        footBallGame.finishGame(eventId, game5.id());
        footBallGame.updateGameScore(eventId, game6.id(), 5, 5);
        footBallGame.finishGame(eventId, game6.id());
        footBallGame.updateGameScore(eventId, game7.id(), 1, 2);
        footBallGame.finishGame(eventId, game7.id());
    }
}
