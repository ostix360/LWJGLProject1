package fr.ostix.game.entity.component.collision;

import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentType;

public class CollisionComponent extends Component {
    private final CollisionProperties properties;

    public CollisionComponent(Entity e, CollisionProperties properties) {
        super(ComponentType.COLLISION_COMPONENT, e);
        this.properties = properties;
    }

    @Override
    public void update() {

    }

    public CollisionProperties getProperties() {
        return properties;
    }
}
