package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.*;
import fr.ostix.game.entity.*;

import java.util.function.*;

public class EntityListener implements Listener {
    // private final List<Entity> entities = new ArrayList<>();
    // public static EntityListener instance = new EntityListener();

    private final Entity e;
    private final Consumer<Entity> whenInteracted;

    public EntityListener(Entity e, Consumer<Entity> whenInteracted) {
        this.e = e;
        this.whenInteracted = whenInteracted;
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {
        if (this.e.equals(e.getEntity())) {
            whenInteracted.accept(e.getEntity());
        }

    }


//    public static EntityListener getInstance() {
//        return instance;
//    }
}
