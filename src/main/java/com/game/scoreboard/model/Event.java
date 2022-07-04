package com.game.scoreboard.model;

import java.util.List;
import java.util.UUID;

public record Event(UUID id, List<Game> activeGames, List<Game> finishedGames) {}
