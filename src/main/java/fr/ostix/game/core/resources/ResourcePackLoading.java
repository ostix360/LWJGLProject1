package fr.ostix.game.core.resources;

import java.util.ArrayList;
import java.util.List;

public class ResourcePackLoading {
    private final List<TextureResources> textures = new ArrayList<>();
    private final List<SoundResources> sounds = new ArrayList<>();
    private final List<ModelResources> models = new ArrayList<>();

    public ResourcePackLoading() {
    }

    public List<TextureResources> getTextures() {
        return textures;
    }

    public List<SoundResources> getSounds() {
        return sounds;
    }

    public List<ModelResources> getModels() {
        return models;
    }
}
