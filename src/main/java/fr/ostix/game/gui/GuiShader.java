package fr.ostix.game.gui;

import fr.ostix.game.graphics.shader.ShaderProgram;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.uniform.MatrixUniform;
import fr.ostix.game.toolBox.OpenGL.uniform.Vector4fUniform;
import org.joml.Matrix4f;

public class GuiShader extends ShaderProgram {

    private final MatrixUniform transformationMatrix = new MatrixUniform("transformationMatrix");
    private final Vector4fUniform layerColor = new Vector4fUniform("layer");

    public GuiShader() {
        super("gui");
        super.getAllUniformLocations(transformationMatrix, layerColor);

        super.validateProgram();
    }

    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0, "position");
    }

    public void loadLayer(Color layer) {
        layerColor.loadVec4fToUniform(layer.getVec4f());
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        transformationMatrix.loadMatrixToUniform(matrix);
    }

}
