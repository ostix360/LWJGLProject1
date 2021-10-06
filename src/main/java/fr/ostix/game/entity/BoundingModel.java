package fr.ostix.game.entity;

import fr.ostix.game.core.collision.react.shape.CollisionModel;

public class BoundingModel {

    private final CollisionModel m;
    private Transform transform;

    public BoundingModel(CollisionModel m) {
        this.m = m;
    }

    public static BoundingModel load(String content) {
        String[] lines = content.split("\n");
        Transform transform = Transform.load(lines[0]);
        CollisionModel cm = loadModel(lines[1]);
        return new BoundingModel(cm).setTransform(transform);
    }

    private static CollisionModel loadModel(String name) {
        return null;
    }

    public CollisionModel getModel() {
        return m;
    }

    public Transform getTransform() {
        return transform;
    }

    public BoundingModel setTransform(Transform transform) {
        this.transform = transform;
        return this;
    }
}
