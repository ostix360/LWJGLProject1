package fr.ostix.game.entity;


import com.flowpowered.react.math.Vector3;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.collision.CollisionComponent;
import fr.ostix.game.entity.component.particle.ParticleComponent;
import fr.ostix.game.graphics.model.Model;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;


public class Entity {

    protected final Vector3 forceToCenter = new Vector3();
    protected final Vector3 torque = new Vector3();
    private final Model model;
    protected Vector3f position;
    protected Vector3f rotation;
    protected float scale;
    private final Transform transform;
    protected MovementType movement;
    private CollisionComponent collision;

    private final List<Component> components = new ArrayList<>();

    public Entity(Model model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation.add(0, 0, 0);
        this.scale = scale;
        this.transform = new Transform(position, rotation, scale);
    }

    public CollisionComponent getCollision() {
        return collision;
    }

    public void setCollision(CollisionComponent collision) {
        this.collision = collision;
    }

    public void addComponent(Component c) {
        components.add(c);
    }

    public void update() {
        for (Component c : components) {
            if (c instanceof ParticleComponent) {
                ((ParticleComponent) c).setOffset(new Vector3f(0, 8.5f, 0));
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
        //transform.setRotation(rotation);
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
        transform.setRotation(rotation);
        transform.setPosition(position);
        transform.setScale(scale);
        return transform;
    }

    public MovementType getMovement() {
        return movement;
    }

    public void setMovement(MovementType movement) {
        this.movement = movement;
    }

    public Vector3 getForceToCenter() {
            return forceToCenter;
    }


    public Vector3 getTorque() {
        return torque.multiply(100);
    }


    public enum MovementType {
        FORWARD("run"),
        BACK("back"),
        JUMP("jump"),
        STATIC("staying");
        String id;

        MovementType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }



}
