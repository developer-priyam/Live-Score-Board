package com.game.scoreboard.exceptions;

/**
 * Exception class to be used for handling Invalid Game Id based operations.
 */
public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
