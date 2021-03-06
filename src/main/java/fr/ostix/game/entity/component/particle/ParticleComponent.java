package fr.ostix.game.entity.component.particle;

import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentType;
import fr.ostix.game.graphics.particles.ParticleSystem;
import fr.ostix.game.graphics.particles.ParticleTarget;
import org.joml.Vector3f;

public class ParticleComponent extends Component {

    private final ParticleSystem system;
    private Vector3f offset = new Vector3f();

    public ParticleComponent(ParticleSystem system,Entity e) {
        super(ComponentType.PARTICLE_COMPONENT,e);
        this.system = system;
    }

    @Override
    public void update() {
        system.update(e.getPosition(), e.getRotation(), e.getScale());
        ParticleTarget target = system.getTarget();
    }

    public void setOffset(Vector3f offset) {
        this.offset = offset;
    }
}
