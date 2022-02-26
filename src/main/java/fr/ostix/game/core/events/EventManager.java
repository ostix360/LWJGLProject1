package fr.ostix.game.core.events;

import java.util.*;

public class EventManager {
    public static final EventManager INSTANCE = new EventManager();
    private final HashSet<EventListener> listeners;

    public EventManager() {
        listeners = new HashSet<>();
    }

    //TODO ...
}
