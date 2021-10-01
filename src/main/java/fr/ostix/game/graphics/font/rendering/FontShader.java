package fr.ostix.game.graphics.font.rendering;


import fr.ostix.game.graphics.shader.ShaderProgram;
import fr.ostix.game.toolBox.OpenGL.uniform.Vector2fUniform;
import fr.ostix.game.toolBox.OpenGL.uniform.Vector3fUniform;
import org.joml.Vector2f;
import org.joml.Vector3f;


public class FontShader extends ShaderProgram {

    private final Vector3fUniform color = new Vector3fUniform("color");
    private final Vector2fUniform translation = new Vector2fUniform("translation");

    public FontShader() {
        super("font");
        super.getAllUniformLocations(color, translation);
        super.validateProgram();
    }

    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    public void loadColor(Vector3f color) {
        this.color.loadVector3fToUniform(color);
    }

    public void loadTranslation(Vector2f translation) {
        this.translation.loadVector2fToUniform(translation);
    }

}
