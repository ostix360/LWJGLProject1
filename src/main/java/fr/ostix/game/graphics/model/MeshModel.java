package fr.ostix.game.graphics.model;

import fr.ostix.game.toolBox.OpenGL.VAO;

public class MeshModel {

    private final VAO vaoID;

    public MeshModel(VAO vaoID) {
        this.vaoID = vaoID;
    }

    public VAO getVAO() {
        return vaoID;
    }

    public int getVertexCount() {
        return vaoID.getVertexCount();
    }
}
