package fr.ostix.game.graphics.render;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.graphics.shader.GuiShader;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.toolBox.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;


import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class GuiRenderer {

    private final MeshModel quadModel;
    private final GuiShader shader;

    public GuiRenderer(Loader loader) {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quadModel = loader.loadToVAO(positions);
        shader = new GuiShader();
    }

    public void render(List<GuiTexture> guis) {
        shader.bind();
        quadModel.getVAO().bind(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        for (GuiTexture gui : guis) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL_TEXTURE_2D,gui.getTexture().getId());
            Matrix4f matrix4f = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
            shader.loadTransformationMatrix(matrix4f);
            glDrawArrays(GL_TRIANGLE_STRIP, 0, quadModel.getVertexCount());
        }
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.unBind();
    }

    public void cleanUp() {
        shader.cleanUp();
    }
}
