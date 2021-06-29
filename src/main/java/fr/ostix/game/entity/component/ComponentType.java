package fr.ostix.game.entity.component;

import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.ai.AICreator;
import fr.ostix.game.entity.component.animation.AnimationCreator;
import fr.ostix.game.entity.component.particle.ParticleCreator;

public enum ComponentType {
    PARTICLE_COMPONENT("Particle Component", new ParticleCreator()),
    AI_COMPONENT("AI Component", new AICreator()),
    ANIMATED_COMPONENT("Animated Component", new AnimationCreator());
    private final String name;
    private final ComponentCreator creator;

    ComponentType(String name, ComponentCreator creator) {
        this.name = name;
        this.creator = creator;
    }

    public Component createComponentToEntity(Entity e) {
        return this.creator.createComponent(e);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
