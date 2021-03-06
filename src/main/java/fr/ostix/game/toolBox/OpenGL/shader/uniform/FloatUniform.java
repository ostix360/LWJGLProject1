package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import org.lwjgl.opengl.GL20;

public class FloatUniform extends Uniform {

    public FloatUniform(String name) {
        super(name);
    }

    public void loadFloatToUniform(float value) {
        GL20.glUniform1f(super.getLocation(), value);
    }
}
