package fr.ostix.game.graphics.particles;

import fr.ostix.game.core.loader.*;
import fr.ostix.game.entity.camera.*;
import org.joml.*;

import java.util.*;

public class MasterParticle {
    private static final Map<ParticleTexture, List<Particle>> particles = new HashMap<>();
    private static ParticleRenderer renderer;

    public static void init(Loader loader, Matrix4f projectionMatrix) {
        renderer = new ParticleRenderer(loader, projectionMatrix);
    }

    public static void addParticle(Particle p) {
        List<Particle> list = particles.computeIfAbsent(p.getTexture(), k -> new ArrayList<>());
        list.add(p);
    }

    public static void update(Camera cam) {
        Iterator<Map.Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<ParticleTexture, List<Particle>> entry = mapIterator.next();
            List<Particle> list = entry.getValue();
            Iterator<Particle> iterator = list.iterator();
            while (iterator.hasNext()) {
                Particle p = iterator.next();
                boolean isAlive = p.isInLife(cam);
                if (!isAlive) {
                    iterator.remove();
                    if (list.isEmpty()) {
                        mapIterator.remove();
                    }
                }
            }
            if (!entry.getKey().isAdditive()) {
                InsertionSort.sortHighToLow(list);
            }
        }

    }

    public static void render(Camera cam) {
        renderer.render(particles, cam);
    }

    public static void cleanUp() {
        renderer.cleanUp();
    }

}
