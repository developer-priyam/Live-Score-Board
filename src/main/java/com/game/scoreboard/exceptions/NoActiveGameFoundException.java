package com.game.scoreboard.exceptions;

public class NoActiveGameFoundException extends RuntimeException{
    public NoActiveGameFoundException(String message) {
        super(message);
    }
}
