package fr.ostix.game.entity.component;

import fr.ostix.game.entity.Entity;

public abstract class Component {
    private final ComponentType type;
    protected final Entity e;

    public Component(ComponentType type, Entity e) {
        this.type = type;
        this.e = e;
    }

    public abstract void update();

    public ComponentType getType() {
        return type;
    }
}
