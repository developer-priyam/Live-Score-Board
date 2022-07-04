package com.game.scoreboard.exceptions;

/**
 * Exception class to be used for handling use case when a game is not active / Finished  and Update Score operation is performed.
 */
public class NoActiveGameFoundException extends RuntimeException{
    public NoActiveGameFoundException(String message) {
        super(message);
    }
}
