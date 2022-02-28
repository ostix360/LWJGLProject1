package fr.ostix.game.core.events.player;

import fr.ostix.game.entity.*;
import org.joml.*;

public class PlayerMoveEvent extends PlayerEvent {
    private final Vector3f newPos;


    public PlayerMoveEvent(Player player, int priority, Vector3f newPos) {
        super(player, priority);
        this.newPos = newPos;
    }
}
