package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.*;

import java.util.*;

public interface InventoryListener extends EventListener {
    void onOpen(InventoryEvent e);

    void onClose(InventoryEvent e);
}
