package fr.ostix.game.core.events;

public class KeyEvent extends Event {
    private final int KEY;

    public KeyEvent(int priority, int key) {
        super(priority);
        this.KEY = key;
    }

    public int getKEY() {
        return KEY;
    }
}
