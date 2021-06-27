package fr.ostix.game.graphics.render;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.Light;
import fr.ostix.game.entity.camera.Camera;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.shader.ClassicShader;
import fr.ostix.game.graphics.shader.TerrainShader;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import fr.ostix.game.toolBox.OpenGL.OpenGlUtils;
import fr.ostix.game.world.Terrain;
import fr.ostix.game.world.skybox.SkyboxRenderer;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer {

    public static final float FOV = (float) Math.toRadians(70f);
    public static final float NEAR = 0.1f;
    public static final float FAR = 500f;

    public static Color skyColor = new Color(0.5444f, 0.62f, 0.69f);

    private final EntityRenderer entityRenderer;
    private final ClassicShader shader = new ClassicShader();

    private final TerrainRenderer terrainRenderer;
    private final TerrainShader terrainShader = new TerrainShader();
    private final SkyboxRenderer skyboxRenderer;

    private List<Terrain> terrains;
    private static Matrix4f projectionMatrix;

    private final Map<Model, List<Entity>> entities = new HashMap<>();

    public MasterRenderer(Loader loader) {
        OpenGlUtils.cullBackFaces(true);
        projectionMatrix = createProjectionMatrix();
        this.entityRenderer = new EntityRenderer(shader, projectionMatrix);
        this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        this.skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
    }


    public void initFrame() {
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(skyColor.getRed(), skyColor.getGreen(), skyColor.getBlue(), skyColor.getAlpha());
        createProjectionMatrix();
    }


    private void processEntity(Entity e) {
        Model model = e.getModel();
        List<Entity> batch = entities.get(model);
        if (batch != null) {
            batch.add(e);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(e);
            entities.put(model, newBatch);
        }
    }

    public void renderScene(List<Entity> entities, List<Terrain> terrains, List<Light> lights, Camera camera) {
        for (Entity entity : entities) {
            processEntity(entity);
        }
        this.terrains = terrains;

        render(lights, camera);
    }

    private void render(List<Light> lights, Camera cam) {
        this.initFrame();
        shader.bind();
        shader.loadLight(lights);
        shader.loadSkyColor(skyColor);
        shader.loadViewMatrix(cam);
        entityRenderer.render(entities);
        shader.unBind();

        terrainShader.bind();
        //terrainShader.loadClipPlane(clipPlane);

        terrainShader.loadSkyColour(skyColor);
        terrainShader.loadLights(lights);
        terrainShader.loadViewMatrix(cam);
        terrainRenderer.render(terrains);
        terrainShader.unBind();
        skyboxRenderer.render(cam, skyColor);

        entities.clear();
    }

    public void cleanUp() {
        this.terrainShader.cleanUp();
        this.shader.cleanUp();
        glDisable(GL_BLEND);
    }

    private Matrix4f createProjectionMatrix() {
        projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        float aspectRatio = (float) DisplayManager.getWidth() / (float) DisplayManager.getHeight();
        projectionMatrix.perspective(FOV, aspectRatio, NEAR, FAR);
        return projectionMatrix;
//        projectionMatrix = new Matrix4f();
//        float aspectRatio = (float) DisplayManager.getWidth() / (float) DisplayManager.getHeight();
//        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
//        float x_scale = y_scale / aspectRatio;
//        float frustum_length = far - near;
//
//        projectionMatrix.set(0,0,x_scale);
//        projectionMatrix.set(1,1,y_scale);
//        projectionMatrix.set(2,2,-((far + near) / frustum_length));
//        projectionMatrix.set(2,3, -1);
//        projectionMatrix.set(3,2, -((2 * near * far) / frustum_length));
//        projectionMatrix.set(3,3, 0);
    }

    public static Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
