package fr.ostix.game.graphics.model;

public class Model {

    private final MeshModel meshModel;
    private final TextureModel textureModel;

    public Model(MeshModel meshModel, TextureModel textureModel) {
        this.meshModel = meshModel;
        this.textureModel = textureModel;
    }

    public MeshModel getMeshModel() {
        return meshModel;
    }

    public TextureModel getModelTexture() {
        return textureModel;
    }
}
