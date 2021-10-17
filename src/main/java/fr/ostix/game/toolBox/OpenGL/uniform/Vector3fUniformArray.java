package fr.ostix.game.toolBox.OpenGL.uniform;

import org.joml.Vector3f;

public class Vector3fUniformArray extends Uniform {

    private final Vector3fUniform[] vector3fUniforms;

    public Vector3fUniformArray(String name, int size) {
        super(name);
        vector3fUniforms = new Vector3fUniform[size];
        for (int i = 0; i < size; i++) {
            vector3fUniforms[i] = new Vector3fUniform(name + "[" + i + "]");
        }
    }

    @Override
    public void storeUniform(int programID) {
        for (Vector3fUniform vector3fUniform : vector3fUniforms) {
            vector3fUniform.storeUniform(programID);
        }
    }

    public void loadVector3fToUniform(Vector3f[] values) {
        for (int i = 0; i < values.length; i++) {
            vector3fUniforms[i].loadVector3fToUniform(values[i]);
        }
    }
}
