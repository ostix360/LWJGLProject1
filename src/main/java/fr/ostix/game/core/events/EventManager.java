package fr.ostix.game.core.events;

import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.core.events.listener.*;

import java.lang.reflect.*;
import java.util.*;

public class EventManager {
    private static final EventManager INSTANCE = new EventManager();
    private final HashSet<Listener> listeners;

    public EventManager() {
        listeners = new HashSet<>();
    }

    public static EventManager getInstance() {
        return INSTANCE;
    }

    public void callEvent(Event e) {
        if (e instanceof KeyEvent) System.out.printf("Event %s called\n", e.getClass().getName());
        // long nano = System.nanoTime();
        this.listeners.forEach(listener -> {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(EventHandler.class)) {
                    try {
                        method.invoke(listener, e);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        //ex.printStackTrace(System.err);
                    }
                }
            }
        });
        //System.out.printf("listener call took %s s.\n",(System.nanoTime()-nano));
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }
}
