package fr.ostix.game.core.events.keyEvent;

import fr.ostix.game.core.events.*;

public class KeyMouseEvent extends Event {
    private final int KEY;

    public KeyMouseEvent(int priority, int key) {
        super(priority);
        this.KEY = key;
    }

    public int getKEY() {
        return KEY;
    }
}
