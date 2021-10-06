package fr.ostix.game.core.collision.react.shape;

public class CollisionModel {
    private final float[] vertices;
    private final float[] indices;

    public CollisionModel(float[] vertices, float[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getIndices() {
        return indices;
    }
}
