package fr.ostix.game.toolBox;

import com.flowpowered.math.imaginary.Quaternionf;
import com.flowpowered.react.math.Quaternion;
import com.flowpowered.react.math.Vector3;
import fr.ostix.game.entity.camera.Camera;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Maths {

    // public static final Logger LOGGER = LogManager.getLogger(Math.class);

    public static float clampf(float value, float min, float max) {
        if (value > max) {
            //   LOGGER.warn("Value : " + value + " > " + "max/!\\");
            return max;
        } else if (value < min) {
            //   LOGGER.warn("Value : " + value + " < " + "min/!\\");
            return min;
        }
        return value;
    }

    public static Matrix4f createTransformationMatrix(Vector2f position, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(position.x(), position.y(), 0);
        matrix.scale(scale.x(), scale.y(), 0);
        return matrix;
    }

    public static Matrix4f createTransformationMatrix(Vector3f position, Vector3f rotation, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(position);
//        matrix.rotateXYZ((float) Math.toRadians(rotation.x()),
//                        (float) Math.toRadians(rotation.y()),
//                        (float) Math.toRadians(rotation.z()));
        matrix.rotate(Math.toRadians(rotation.x()), new Vector3f(1, 0, 0));
        matrix.rotate(Math.toRadians(rotation.y()), new Vector3f(0, 1, 0));
        matrix.rotate(Math.toRadians(rotation.z()), new Vector3f(0, 0, 1));
        matrix.scale(new Vector3f(scale, scale, scale));
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera cam) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.identity();
        matrix4f.rotate((float) Math.toRadians(cam.getPitch()), new Vector3f(1, 0, 0));
        matrix4f.rotate((float) Math.toRadians(cam.getYaw()), new Vector3f(0, 1, 0));
        matrix4f.rotate((float) Math.toRadians(cam.getRoll()), new Vector3f(0, 0, 1));
        Vector3f cameraPos = cam.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        matrix4f.translate(negativeCameraPos);
        return matrix4f;
    }

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static Vector3f toVector3f(Vector3 value) {
        return new Vector3f(value.getX(), value.getY(), value.getZ());
    }

    public static Quaternionf toMathQuaternion(Quaternion q) {
        return new Quaternionf(q.getX(), q.getY(), q.getZ(), q.getW());
    }

    /**
     * Converts from Math to React Quaternion
     *
     * @param q The Math Quaternion
     * @return The equivalent React Quaternion
     */
    public static Quaternion toReactQuaternion(Quaternionf q) {
        return new Quaternion(q.getX(), q.getY(), q.getZ(), q.getW());
    }


    public static org.joml.Quaternionf convertRotationToQuaternionf(Vector3f rotation) {
        double angle = (rotation.x()+rotation.y()+rotation.z())/360*3;
        double x = rotation.x()/angle;
        double y = rotation.y()/angle;
        double z = rotation.z()/angle;
        return new org.joml.Quaternionf().setAngleAxis(Math.toRadians(angle),x,y,z);
    }


    /**
     * Creates a new quaternion from the rotation around the axis.
     *
     * @param angle The angle of the rotation
     * @param x The x component of the axis
     * @param y The y component of the axis
     * @param z The z component of the axis
     * @return The quaternion
     */
    public static Quaternion angleAxisToQuaternion(float angle, float x, float y, float z) {
        final float halfAngle = (float) (java.lang.Math.toRadians(angle) / 2);
        final float q = (float) (java.lang.Math.sin(halfAngle) / java.lang.Math.sqrt(x * x + y * y + z * z));
        return new Quaternion(x * q, y * q, z * q, (float) java.lang.Math.cos(halfAngle));
    }
}
