package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.*;

import java.util.*;

public interface InteractionKeyListener extends EventListener {
    void onKeyPressed(KeyEvent e);

    void onKeyReleased(KeyEvent e);
}
