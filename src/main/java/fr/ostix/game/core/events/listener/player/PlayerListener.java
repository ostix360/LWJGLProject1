package fr.ostix.game.core.events.listener.player;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.toolBox.*;

public class PlayerListener implements Listener {


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        p.getForceToCenter().add(Maths.toVector3(e.getDeltaPos()));
    }
}
