package fr.ostix.game.graphics.render;

import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.camera.ICamera;
import fr.ostix.game.graphics.shader.AnimatedModelShader;
import fr.ostix.game.graphics.textures.Texture;
import fr.ostix.game.toolBox.OpenGL.OpenGlUtils;
import fr.ostix.game.toolBox.OpenGL.VAO;
import org.joml.Vector3f;


/**
 * This class deals with rendering an animated entity. Nothing particularly new
 * here. The only exciting part is that the joint transforms get loaded up to
 * the shader in a uniform array.
 *
 * @author Karl
 */
public class AnimatedModelRenderer {

    private final AnimatedModelShader shader;

    /**
     * Initializes the shader program used for rendering animated models.
     */
    public AnimatedModelRenderer() {
        this.shader = new AnimatedModelShader();
    }

    /**
     * Renders an animated entity. The main thing to note here is that all the
     * joint transforms are loaded up to the shader to a uniform array. Also 5
     * attributes of the VAO are enabled before rendering, to include joint
     * indices and weights.
     *
     * @param entity   - the animated entity to be rendered.
     * @param camera   - the camera used to render the entity.
     * @param lightDir - the direction of the light in the scene.
     */
    public void render(AnimatedModel entity, ICamera camera, Vector3f lightDir) {
        prepare(camera, lightDir);

//        shader.transformation.loadMatrixToUniform(entity.getTransform().getTransformation());
//
//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        entity.getModel().getModelTexture().bindTexture();
//        entity.getModel().getMeshModel().getVAO().bind(0, 1, 2,3,4);
        shader.jointTransforms.loadMatrixArray(entity.getJointTransforms());
        //  GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getMeshModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        VAO.unbind(0, 1, 2, 3, 4);
        Texture.unBindTexture();
        finish();
    }

    /**
     * Deletes the shader program when the game closes.
     */
    public void cleanUp() {
        shader.cleanUp();
    }

    /**
     * Starts the shader program and loads up the projection view matrix, as
     * well as the light direction. Enables and disables a few settings which
     * should be pretty self-explanatory.
     *
     * @param camera   - the camera being used.
     * @param lightDir - the direction of the light in the scene.
     */
    private void prepare(ICamera camera, Vector3f lightDir) {
        shader.bind();
        shader.projectionViewMatrix.loadMatrixToUniform(camera.getProjectionViewMatrix());
        shader.lightDirection.loadVector3fToUniform(lightDir);

        OpenGlUtils.antialias(true);
        OpenGlUtils.disableBlending();
        OpenGlUtils.enableDepthTesting(true);
    }

    /**
     * Stops the shader program after rendering the entity.
     */
    private void finish() {
        shader.unBind();
    }

}
