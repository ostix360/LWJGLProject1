package fr.ostix.game.graphics.skybox;


import fr.ostix.game.entity.camera.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.shader.*;
import fr.ostix.game.toolBox.OpenGL.shader.uniform.*;
import org.joml.*;

import java.lang.Math;

public class SkyboxShader extends ShaderProgram {

    private final MatrixUniform projectionMatrix = new MatrixUniform("projectionMatrix");
    private final MatrixUniform viewMatrix = new MatrixUniform("viewMatrix");
    private final Vector3fUniform fogColor = new Vector3fUniform("fogColor");
    private final IntUniform texture1 = new IntUniform("cubeMap");
    private final IntUniform texture2 = new IntUniform("cubeMap2");
    private final FloatUniform blendFactor = new FloatUniform("blendFactor");

    private float rotate;

    public SkyboxShader() {
        super("skyboxShader");
        super.storeAllUniformsLocations(projectionMatrix, viewMatrix, fogColor, texture1, texture2, blendFactor);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        projectionMatrix.loadMatrixToUniform(matrix);
    }

    public void loadViewMatrix(Camera camera,float rotate) {
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30(0);
        matrix.m31(0);
        matrix.m32(0);
        matrix.rotate((float) Math.toRadians(rotate), new Vector3f(0, 1, 0));
        viewMatrix.loadMatrixToUniform(matrix);
    }

    public void connectTextureUnits() {
        texture1.loadIntToUniform(0);
        texture2.loadIntToUniform(1);
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
