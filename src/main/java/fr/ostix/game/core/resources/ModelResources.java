package fr.ostix.game.core.resources;

public class ModelResources {
    private final String name;
    private final String path;
    private final String texture;

    public ModelResources(String name, String path, String texture) {
        this.name = name;
        this.path = path;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getTexture() {
        return texture;
    }
}
