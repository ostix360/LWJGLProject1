package fr.ostix.game.graphics.shader;

import fr.ostix.game.openGLUtils.uniform.MatrixUniform;
import org.joml.Matrix4f;

public class GuiShader extends ShaderProgram{

    private final MatrixUniform transformationMatrix = new MatrixUniform("transformationMatrix");

    public GuiShader() {
        super("Gui");
        super.getAllUniformLocations(transformationMatrix);


        super.validateProgram();
    }

    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0,"position");
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        transformationMatrix.loadMatrixToUniform(matrix);
    }

}
