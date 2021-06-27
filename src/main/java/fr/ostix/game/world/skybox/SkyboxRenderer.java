package fr.ostix.game.world.skybox;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.entity.camera.Camera;
import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.VAO;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.*;

public class SkyboxRenderer {

    private static final float SIZE = 600f;

    private static final float[] VERTICES = {
            -SIZE, SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, SIZE,
            -SIZE, -SIZE, SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, SIZE,

            -SIZE, SIZE, -SIZE,
            SIZE, SIZE, -SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            -SIZE, SIZE, SIZE,
            -SIZE, SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE, SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE, SIZE,
            SIZE, -SIZE, SIZE
    };

    private static final String[] FILES_DAY = {"day/right", "day/left", "day/top", "day/bottom", "day/back", "day/front"};
    private static final String[] FILES_NIGHT = {"night/right", "night/left", "night/top", "night/bottom", "night/back", "night/front"};

    private final MeshModel model;
    private final int dayTexture;
    private final int nightTexture;
    private final SkyboxShader shader;
    private float time = 5000;
    private float rotate = 0;

    public SkyboxRenderer(Loader loader, Matrix4f projectionMatrix) {
        model = loader.loadToVAO(VERTICES);
        dayTexture = loader.loadCubMap(FILES_DAY);
        nightTexture = loader.loadCubMap(FILES_NIGHT);
        shader = new SkyboxShader();
        shader.bind();
        shader.connectTextureUnits();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.unBind();
    }

    public void update(){
        rotate++;
        //bindTextures();
    }

    public void render(Camera cam, Color fog) {
        shader.bind();
        shader.loadViewMatrix(cam,rotate);
        shader.loadFogColor(fog);
        model.getVAO().bind(0);

        glDrawArrays(GL_TRIANGLES, 0, model.getVertexCount());
        VAO.unbind(0);
        shader.unBind();
    }

    private void bindTextures() {
        time ++;
        //time  *;
        int texture1;
        int texture2;
        float blendFactor;
        if (time >= 0 && time < 5000) {
            texture1 = nightTexture;
            texture2 = nightTexture;
            blendFactor = (time) / (5000);
        } else if (time >= 5000 && time < 8000) {
            texture1 = nightTexture;
            texture2 = dayTexture;
            blendFactor = (time - 5000) / (8000 - 5000);
        } else if (time >= 8000 && time < 21000) {
            texture1 = dayTexture;
            texture2 = dayTexture;
            blendFactor = (time - 8000) / (21000 - 8000);
        } else {
            texture1 = dayTexture;
            texture2 = nightTexture;
            blendFactor = (time - 21000) / (24000 - 21000);
        }
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
        shader.loadBlendFactor(blendFactor);
    }
}
