package com.game.scoreboard.model;

import java.util.UUID;

/**
 * Game have gamedetails object because we will receive the game details from user but we will be generating the unique id / index when we store the data in DB (here in memory map)
 */
public record Game(UUID id, GameDetails gameDetails) { }
