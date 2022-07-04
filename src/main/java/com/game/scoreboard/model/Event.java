package com.game.scoreboard.model;

import java.util.List;
import java.util.UUID;

/**
 * Update only gets performed on Active Games.
 *
 * Summary is only generated on Finsihed games.
 *
 * That is why, there are 2 separate lists.
 *
 * Event Class is also created on top of Game class, so as to make it extendable for have multiple football or in-general any game events.
 */
public record Event(UUID id, List<Game> activeGames, List<Game> finishedGames) {}
