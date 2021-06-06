package fr.ostix.game.entity;

import fr.ostix.game.toolBox.Maths;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    private final Vector3f position;
    private Vector3f rotation;
    private final float scale;
    private Matrix3f rotationMatrix;

    public Transform(Vector3f position, Vector3f rotation,float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setQ(Quaternionf q) {
        rotationMatrix = new Matrix3f().identity();
        rotationMatrix.rotate(q);
    }

    public Matrix4f getTransformation(){
        Matrix4f m = Maths.createTransformationMatrix(this.position,this.rotation,this.scale);
        if (rotationMatrix != null)m.mul(rotationMatrix.get(new Matrix4f()));
        return m;
    }
}