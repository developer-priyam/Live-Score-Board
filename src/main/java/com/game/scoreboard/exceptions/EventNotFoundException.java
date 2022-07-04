package com.game.scoreboard.exceptions;

/**
 * Exception class to be used for handling Invalid Event Id based operations.
 */
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String msg) {
        super(msg);
    }
}
