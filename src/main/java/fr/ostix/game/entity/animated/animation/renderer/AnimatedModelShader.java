package fr.ostix.game.entity.animated.animation.renderer;

import fr.ostix.game.graphics.render.AnimatedModelRenderer;
import fr.ostix.game.graphics.shader.ShaderProgram;
import fr.ostix.game.openGLToolBox.uniform.MatrixUniform;
import fr.ostix.game.openGLToolBox.uniform.MatrixUniformArray;
import fr.ostix.game.openGLToolBox.uniform.Vector3fUniform;

public class AnimatedModelShader extends ShaderProgram {

    private static final int MAX_JOINTS = 50;// max number of joints in a skeleton


    public MatrixUniform projectionViewMatrix = new MatrixUniform("projectionViewMatrix");
    public Vector3fUniform lightDirection = new Vector3fUniform("lightDirection");
    public MatrixUniformArray jointTransforms = new MatrixUniformArray("jointTransforms", MAX_JOINTS);
    public MatrixUniform transformation = new MatrixUniform("transformationMatrix");

    /**
     * Creates the shader program for the {@link AnimatedModelRenderer} by
     * loading up the vertex and fragment shader code files. It also gets the
     * location of all the specified uniform variables, and also indicates that
     * the diffuse texture will be sampled from texture unit 0.
     */
    public AnimatedModelShader() {
        super("animation");
        super.getAllUniformLocations(projectionViewMatrix, transformation, lightDirection, jointTransforms);
        super.validateProgram();
    }


    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0, "in_position");
        super.bindAttribute(1, "in_textureCoords");
        super.bindAttribute(2, "in_normal");
        super.bindAttribute(3, "in_jointIndices");
        super.bindAttribute(4, "in_weights");
    }
}
