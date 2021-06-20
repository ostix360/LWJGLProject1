package fr.ostix.game.core.resources;

import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.model.Texture;

import java.util.HashMap;

public class ResourcePack {
    private final HashMap<String, Texture> textures;
    private final HashMap<String, SoundSource> sounds;
    private final HashMap<String, Model> models;

    public ResourcePack(HashMap<String, Texture> textures, HashMap<String, SoundSource> sounds, HashMap<String, Model> models) {
        this.textures = textures;
        this.sounds = sounds;
        this.models = models;
    }

    public HashMap<String, Texture> getTextureByName() {
        return textures;
    }

    public HashMap<String, SoundSource> getSoundByName() {
        return sounds;
    }

    public HashMap<String, Model> getModelByName() {
        return models;
    }
}
