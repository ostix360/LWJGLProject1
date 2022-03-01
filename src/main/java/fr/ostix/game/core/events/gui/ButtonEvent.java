package fr.ostix.game.core.events.gui;

import fr.ostix.game.core.events.*;
import fr.ostix.game.menu.component.*;

public class ButtonEvent extends Event {

    private final Button button;

    public ButtonEvent(Button button, int priority) {
        super(priority);
        this.button = button;
    }

    public Button getButton() {
        return button;
    }
}
