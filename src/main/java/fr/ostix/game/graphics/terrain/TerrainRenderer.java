package fr.ostix.game.graphics.terrain;

import fr.ostix.game.entity.camera.Camera;
import fr.ostix.game.entity.component.light.Light;
import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.graphics.textures.Texture;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.Maths;
import fr.ostix.game.toolBox.OpenGL.OpenGlUtils;
import fr.ostix.game.toolBox.OpenGL.VAO;
import fr.ostix.game.world.Terrain;
import fr.ostix.game.world.texture.TerrainTexturePack;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL13;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class TerrainRenderer {
    private final TerrainShader shader;

    public TerrainRenderer(TerrainShader terrainShader, Matrix4f projectionMatrix) {
        this.shader = terrainShader;
        shader.bind();
        shader.connectTerrainUnits();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.unBind();
    }

    public void render(List<Terrain> terrains, List<Light> lights, Camera cam, Color skyColor, Vector4f clipPlane) {
        prepare(lights, skyColor, cam, clipPlane);
        OpenGlUtils.goWireframe(false);
        // shader.loadShaderMapSpace(toShadowSpace);
        for (Terrain ter : terrains) {
            prepareTerrain(ter);
            loadModelMatrix(ter);
            glDrawElements(GL_TRIANGLES, ter.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }

    private void prepare(List<Light> lights, Color skyColor, Camera cam, Vector4f clipPlane) {
        shader.bind();
        shader.loadClipPlane(clipPlane);
        shader.loadSkyColour(skyColor);
        shader.loadLight(lights);
        shader.loadViewMatrix(cam);
    }

    private void prepareTerrain(Terrain terrain) {
        MeshModel model = terrain.getModel();
        model.getVAO().bind(0, 1, 2);
        shader.loadSpecular(0, 1);
        bindTexture(terrain);
    }

    private void bindTexture(Terrain ter) {
        TerrainTexturePack texturePack = ter.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        glBindTexture(GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        glBindTexture(GL_TEXTURE_2D, ter.getBlendMap().getTextureID());
    }

    private void unbindTexturedModel() {
        Texture.unBindTexture();
        VAO.unbind(0, 1, 2);
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()),
                new Vector3f(0, 0, 0), new Vector3f(1));
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
