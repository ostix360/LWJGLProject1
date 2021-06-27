package fr.ostix.game.graphics.shader;

import fr.ostix.game.entity.Light;
import fr.ostix.game.entity.camera.Camera;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.Maths;
import fr.ostix.game.toolBox.OpenGL.uniform.*;
import fr.ostix.game.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;

public class ClassicShader extends ShaderProgram {

    private final int MAX_LIGHTS = World.MAX_LIGHTS;

    private final MatrixUniform transformationMatrix = new MatrixUniform("transformationMatrix");
    private final MatrixUniform projectionMatrix = new MatrixUniform("projectionMatrix");
    private final MatrixUniform viewMatrix = new MatrixUniform("viewMatrix");
    private final Vector3fUniform[] lightPos = new Vector3fUniform[MAX_LIGHTS];
    private final Vector3fUniform[] lightColor = new Vector3fUniform[MAX_LIGHTS];
    private final Vector3fUniform[] lightAttenuation = new Vector3fUniform[MAX_LIGHTS];
    private final FloatUniform[] lightPower = new FloatUniform[MAX_LIGHTS];
    private final FloatUniform reflectivity = new FloatUniform("reflectivity");
    private final FloatUniform shine = new FloatUniform("shine");
    public final MatrixUniformArray jointTransforms = new MatrixUniformArray("jointTransforms", 50);
    public final BooleanUniform isAnimated = new BooleanUniform("isAnimated");
    private final Vector3fUniform skyColor = new Vector3fUniform("skyColor");

    public ClassicShader() {
        super("shader");
        initLightsUniform();
        super.getAllUniformLocations(transformationMatrix, projectionMatrix, viewMatrix,
                reflectivity, shine, skyColor, jointTransforms, isAnimated);
        super.getAllUniformLocations(lightPos);
        super.getAllUniformLocations(lightColor);
        super.getAllUniformLocations(lightAttenuation);
        super.getAllUniformLocations(lightPower);
        super.validateProgram();
    }

    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normals");
        super.bindAttribute(3, "jointIndices");
        super.bindAttribute(4, "weights");
    }


    // light

    protected void initLightsUniform() {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            lightPos[i] = new Vector3fUniform("lightPos[" + i + "]");
            lightColor[i] = new Vector3fUniform("lightColor[" + i + "]");
            lightAttenuation[i] = new Vector3fUniform("attenuation[" + i + "]");
            lightPower[i] = new FloatUniform("lightPower[" + i + "]");
        }
    }

    public void loadLight(List<Light> lights) {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                Light light = lights.get(i);
                lightPos[i].loadVector3fToUniform(light.getPosition());
                lightColor[i].loadVector3fToUniform(light.getColourVec3f());
                lightAttenuation[i].loadVector3fToUniform(light.getAttenuation());
                lightPower[i].loadFloatToUniform(light.getPower());
            } else {
                lightPos[i].loadVector3fToUniform(new Vector3f(0, 0, 0));
                lightColor[i].loadVector3fToUniform(new Vector3f(0, 0, 0));
                lightAttenuation[i].loadVector3fToUniform(new Vector3f(1, 0, 0));
                lightPower[i].loadFloatToUniform(0F);
            }
        }
    }

    public void loadSpecular(float reflectivity, float shineDamper) {
        this.reflectivity.loadFloatToUniform(reflectivity);
        this.shine.loadFloatToUniform(shineDamper);
    }


    // Projection Transformation View Matrix

    public void loadTransformationMatrix(Matrix4f matrix) {
        transformationMatrix.loadMatrixToUniform(matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        projectionMatrix.loadMatrixToUniform(matrix);
    }

    public void loadViewMatrix(Camera cam) {
        viewMatrix.loadMatrixToUniform(Maths.createViewMatrix(cam));
    }


    public void loadSkyColor(Color skyColor) {
        this.skyColor.loadVector3fToUniform(skyColor.getVec3f());
    }
}
