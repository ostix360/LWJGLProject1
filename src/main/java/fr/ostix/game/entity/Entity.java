package fr.ostix.game.entity;


import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.particle.ParticleComponent;
import fr.ostix.game.graphics.model.Model;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;


public class Entity {

    private final Model model;
    protected Vector3f position;
    protected Vector3f rotation;
    protected float scale;
    private Transform transform;

    private final List<Component> components = new ArrayList<>();

    public Entity(Model model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void addComponent(Component c) {
        components.add(c);
    }

    public void update() {
        for (Component c : components) {
            if (c instanceof ParticleComponent){
                ((ParticleComponent) c).setOffset(new Vector3f(0,8.5f,0));
            }
            c.update();
        }
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void increasePosition(Vector3f value) {
        position.add(value);
    }

    public void increaseRotation(Vector3f value) {
        rotation.add(value);
        transform.setRotation(rotation);
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Transform getTransform() {
        return transform = new Transform(position, rotation, scale);
    }


}
