package fr.ostix.game.entity;

import fr.ostix.game.toolBox.Maths;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position;
    private Vector3f rotation;
    private float scale;
    private Matrix3f rotationMatrix;

    public Transform(Vector3f position, Vector3f rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public static Transform load(String values) {
        String[] value = values.split(";");
        int index = 0;
        Vector3f pos = new Vector3f(Float.parseFloat(value[index]), Float.parseFloat(value[index++]),
                Float.parseFloat(value[index++]));
        Vector3f rot = new Vector3f(Float.parseFloat(value[index++]), Float.parseFloat(value[index++]),
                Float.parseFloat(value[index++]));
        float scale = Float.parseFloat(value[index]);
        return new Transform(pos, rot, scale);
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

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
