package fr.ostix.game.graphics.shader;


import fr.ostix.game.entity.Light;
import fr.ostix.game.entity.camera.Camera;
import fr.ostix.game.openGLUtils.uniform.*;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.Maths;
import fr.ostix.game.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;


public class TerrainShader extends ShaderProgram {

    private final int MAX_LIGHTS = World.MAX_LIGHTS;

    private final MatrixUniform transformationMatrix = new MatrixUniform("transformationMatrix");
    private final MatrixUniform projectionMatrix = new MatrixUniform("projectionMatrix");
    private final MatrixUniform viewMatrix = new MatrixUniform("viewMatrix");
    private final Vector3fUniform[] lightPos = new Vector3fUniform[MAX_LIGHTS];
    private final Vector3fUniform[] lightColor = new Vector3fUniform[MAX_LIGHTS];
    private final Vector3fUniform[] lightAttenuation = new Vector3fUniform[MAX_LIGHTS];
    private final FloatUniform[] lightPower = new FloatUniform[MAX_LIGHTS];
    private final FloatUniform shine = new FloatUniform("shine");
    private final FloatUniform reflectivity = new FloatUniform("reflectivity");
    private final Vector3fUniform skyColour = new Vector3fUniform("skyColor");
    private final IntUniform backgroundTexture = new IntUniform("backgroundTexture");
    private final IntUniform rTexture = new IntUniform("rTexture");
    private final IntUniform gTexture = new IntUniform("gTexture");
    private final IntUniform bTexture = new IntUniform("bTexture");
    private final IntUniform blendMap = new IntUniform("blendMap");
    private final Vector4fUniform plane = new Vector4fUniform("plane");
    private final MatrixUniform toShadowMapSpace = new MatrixUniform("toShadowMapSpace");
    private final IntUniform shadowMap = new IntUniform("shadowMap");

    public TerrainShader() {
        super("terrainShader");
        initLightsUniform();
        super.getAllUniformLocations(transformationMatrix, projectionMatrix, viewMatrix
                , reflectivity, shine, backgroundTexture, rTexture, gTexture, bTexture, blendMap, skyColour);
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
        super.bindAttribute(2, "normal");
    }


    protected void initLightsUniform() {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            lightPos[i] = new Vector3fUniform("lightPosition[" + i + "]");
            lightColor[i] = new Vector3fUniform("lightColour[" + i + "]");
            lightAttenuation[i] = new Vector3fUniform("attenuation[" + i + "]");
            lightPower[i] = new FloatUniform("lightPower[" + i + "]");
        }
    }


    public void connectTerrainUnits() {
        backgroundTexture.loadIntToUniform(0);
        rTexture.loadIntToUniform(1);
        gTexture.loadIntToUniform(2);
        bTexture.loadIntToUniform(3);
        blendMap.loadIntToUniform(4);
        shadowMap.loadIntToUniform(5);
    }


    public void loadShaderMapSpace(Matrix4f matrix) {
        toShadowMapSpace.loadMatrixToUniform(matrix);
    }

    public void loadClipPlane(Vector4f value) {
        plane.loadVec4fToUniform(value);
    }

    public void loadSkyColour(Color colour) {
        skyColour.loadVector3fToUniform(colour.getVec3f());
    }

    public void loadSpecular(float reflectivity, float shineDamper) {
        this.reflectivity.loadFloatToUniform(reflectivity);
        this.shine.loadFloatToUniform(shineDamper);
    }

    public void loadLights(List<Light> lights) {
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

}
