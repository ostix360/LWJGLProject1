package fr.ostix.game.world.skybox;


import fr.ostix.game.entity.camera.Camera;
import fr.ostix.game.graphics.shader.ShaderProgram;
import fr.ostix.game.openGLUtils.uniform.FloatUniform;
import fr.ostix.game.openGLUtils.uniform.IntUniform;
import fr.ostix.game.openGLUtils.uniform.MatrixUniform;
import fr.ostix.game.openGLUtils.uniform.Vector3fUniform;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SkyboxShader extends ShaderProgram {

    private static final float ROTATE_SPEED = 3f;

    private final MatrixUniform projectionMatrix = new MatrixUniform("projectionMatrix");
    private final MatrixUniform viewMatrix = new MatrixUniform("viewMatrix");
    private final Vector3fUniform fogColor = new Vector3fUniform("fogColor");
    private final IntUniform texture1 = new IntUniform("cubeMap");
    private final FloatUniform blendFactor = new FloatUniform("blendFactor");

    private float rotate;

    public SkyboxShader() {
        super("skyboxShader");
        super.getAllUniformLocations(projectionMatrix, viewMatrix, fogColor, texture1, blendFactor);
        super.validateProgram();
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        projectionMatrix.loadMatrixToUniform(matrix);
    }

    public void loadViewMatrix(Camera camera, float rotate) {
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30(0);
        matrix.m31(0);
        matrix.m32(0);
        matrix.rotate((float) Math.toRadians(rotate), new Vector3f(0, 1, 0));
        viewMatrix.loadMatrixToUniform(matrix);
    }

    public void connectTextureUnits() {
        texture1.loadIntToUniform(0);
    }

    public void loadBlendFactor(float factor) {
        blendFactor.loadFloatToUniform(factor);
    }

    public void loadFogColor(Color fog) {
        fogColor.loadVector3fToUniform(fog.getVec3f());
    }

    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0, "position");
    }
}
