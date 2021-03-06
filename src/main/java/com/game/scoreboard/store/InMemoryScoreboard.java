package com.game.scoreboard.store;

import com.game.scoreboard.model.Event;

import java.util.*;

/**
 * In Memory Scoreboard score. It uses a simple Hashmap for storing the dta of each Event.
 *
 * Implements Singleton Design Pattern
 */
public class InMemoryScoreboard {

    private static InMemoryScoreboard inMemoryScoreboard;

    private final Map<UUID, Event> eventMap;

    private InMemoryScoreboard() {
        this.eventMap = new HashMap<>();
    }

    public static InMemoryScoreboard getInstance() {
        if(inMemoryScoreboard == null) {
            synchronized (InMemoryScoreboard.class) {
                if (inMemoryScoreboard == null) {
                    inMemoryScoreboard = new InMemoryScoreboard();
                }
            }
        }
        return inMemoryScoreboard;
    }

    public Map<UUID, Event> getEventMap() {
        return eventMap;
    }

    public void setEventMap(UUID key, Event value) {
        eventMap.put(key, value);
    }


}
