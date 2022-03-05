package fr.ostix.game.core.events.player;

import fr.ostix.game.entity.*;
import org.joml.*;

public class PlayerMoveEvent extends PlayerEvent {
    private final Vector3f deltaPos;


    public PlayerMoveEvent(Player player, int priority, Vector3f deltaPos) {
        super(player, priority);
        this.deltaPos = deltaPos;
    }

    public Vector3f getDeltaPos() {
        return deltaPos;
    }
}
