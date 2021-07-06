package fr.ostix.game.toolBox;

import com.flowpowered.math.imaginary.Quaternionf;
import fr.ostix.game.core.collision.react.maths.Vector3;
import fr.ostix.game.entity.camera.Camera;
import org.joml.Math;
import org.joml.*;
import org.lwjgl.assimp.AIMatrix4x4;

import java.util.Random;

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
        matrix4f.rotate(Math.toRadians(cam.getPitch()), new Vector3f(1, 0, 0));
        matrix4f.rotate(Math.toRadians(cam.getYaw()), new Vector3f(0, 1, 0));
        matrix4f.rotate(Math.toRadians(cam.getRoll()), new Vector3f(0, 0, 1));
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


    /*
     *
     *
     * Math for the particle spawn
     *
     *
     */

    public static Vector3f rotateVector(Vector3f direction, float rotX, float rotY, float rotZ) {

        Matrix4f matrix = createTransformationMatrix(new Vector3f(0.0F, 0.0F, 0.0F),
                new Vector3f(rotX, rotY, rotZ), 1.0F);
        /* 118 */
        Vector4f direction4 = new Vector4f(direction.x, direction.y, direction.z, 1.0F);
        /* 119 */
        matrix.transform(direction4, direction4);
        /* 120 */
        return new Vector3f(direction4.x, direction4.y, direction4.z);
        /*     */
    }

    public static Vector3f generateRandomUnitVector() {
        /*  72 */
        java.util.Random random = new Random();
        /*  73 */
        float theta = (float) (random.nextFloat() * 2.0F * java.lang.Math.PI);
        /*  74 */
        float z = random.nextFloat() * 2.0F - 1.0F;
        /*  75 */
        float rootOneMinusZSquared = (float) java.lang.Math.sqrt(1.0F - z * z);
        /*  76 */
        float x = (float) (rootOneMinusZSquared * java.lang.Math.cos(theta));
        /*  77 */
        float y = (float) (rootOneMinusZSquared * java.lang.Math.sin(theta));
        /*  78 */
        return new Vector3f(x, y, z);
        /*     */
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

    public static Matrix4f fromAssimpMatrix(AIMatrix4x4 AIm) {
        Matrix4f m = new Matrix4f().identity();

        m.m00(AIm.a1());
        m.m01(AIm.a2());
        m.m02(AIm.a3());
        m.m03(AIm.a4());

        m.m10(AIm.b1());
        m.m11(AIm.b2());
        m.m12(AIm.b3());
        m.m13(AIm.b4());

        m.m20(AIm.c1());
        m.m21(AIm.c2());
        m.m22(AIm.c3());
        m.m23(AIm.c4());

        m.m30(AIm.d1());
        m.m31(AIm.d2());
        m.m32(AIm.d3());
        m.m33(AIm.d4());
        return m;
    }
}
