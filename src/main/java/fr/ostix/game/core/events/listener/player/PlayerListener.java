package fr.ostix.game.core.events.listener.player;

import com.flowpowered.react.math.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.entity.*;

public class PlayerListener implements Listener {


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        switch (p.getMovement()) {
            case FORWARD:
                float dx = (float) (0.001f * Math.sin(Math.toRadians(p.getRotation().y())));
                float dz = (float) (0.001f * Math.cos(Math.toRadians(p.getRotation().y())));
                p.getForceToCenter().set(new Vector3(dx, 0, dz));
                break;
            case BACK:
                dx = -(float) (Math.sin(Math.toRadians(p.getRotation().y())));
                dz = -(float) (Math.cos(Math.toRadians(p.getRotation().y())));
                p.getForceToCenter().set(new Vector3(dx, 0, dz));
                break;
            default:
                break;

        }
    }
}
