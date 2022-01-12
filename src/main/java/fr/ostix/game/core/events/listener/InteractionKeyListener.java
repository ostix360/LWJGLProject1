package fr.ostix.game.core.events.listener;

import java.util.*;

public interface InteractionKeyListener extends EventListener {
    void onKeyPressed();

    void onKeyReleased();
}
