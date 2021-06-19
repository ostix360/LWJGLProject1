package fr.ostix.game.graphics.render;

import fr.ostix.game.entity.Entity;
import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.model.Texture;
import fr.ostix.game.graphics.shader.ClassicShader;
import fr.ostix.game.openGLUtils.VAO;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL13;

import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class EntityRenderer implements IRenderer {

    ClassicShader shader;

    public EntityRenderer(ClassicShader shader,Matrix4f projectionMatrix) {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        this.shader = shader;
        this.shader.bind();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.unBind();
    }

    public void render(Map<Model, List<Entity>> entities) {
        for (Model model : entities.keySet()) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                prepareInstance(entity);
                glDrawElements(GL_TRIANGLES, entity.getModel().getMeshModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            }
            finish();
        }
    }

    @Override
    public void prepareInstance(Entity entity) {
        shader.loadTransformationMatrix(entity.getTransform().getTransformation());
    }

    @Override
    public void prepareTexturedModel(Model model) {
        MeshModel meshModel = model.getMeshModel();
        meshModel.getVAO().bind(0, 1, 2);

        Texture texture = model.getModelTexture();
        shader.loadSpecular(texture.getReflectivity(), texture.getShineDamper());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bindTexture();
    }


    @Override
    public void finish() {
        MasterRenderer.enableCulling();
        VAO.unbind(0, 1, 2);
        Texture.unBindTexture();
    }


}
