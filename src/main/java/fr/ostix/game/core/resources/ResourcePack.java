package fr.ostix.game.core.resources;

import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.animated.animation.animation.Animation;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.model.Texture;

import java.util.HashMap;

public class ResourcePack {
    private static HashMap<String, Texture> textures;
    private final HashMap<String, SoundSource> sounds;
    private final HashMap<String, Model> models;
    private static HashMap<AnimatedModel, HashMap<String, Animation>> animations;
    private final HashMap<String, AnimatedModel> animatedModels;
    private final HashMap<Integer, String> components;

    public ResourcePack(HashMap<String, Texture> textures, HashMap<String, SoundSource> sounds,
                        HashMap<String, Model> models, HashMap<String, AnimatedModel> animatedModelByName,
                        HashMap<AnimatedModel, HashMap<String, Animation>> animations, HashMap<Integer, String> components) {
        ResourcePack.textures = textures;
        this.sounds = sounds;
        this.models = models;
        this.animatedModels = animatedModelByName;
        ResourcePack.animations = animations;
        this.components = components;
    }

    public static HashMap<String, Texture> getTextureByName() {
        return textures;
    }

    public HashMap<String, SoundSource> getSoundByName() {
        return sounds;
    }

    public HashMap<String, Model> getModelByName() {
        return models;
    }

    public static HashMap<AnimatedModel, HashMap<String, Animation>> getAnimationByName() {
        return animations;
    }

    public HashMap<String, AnimatedModel> getAnimatedModelByName() {
        return animatedModels;
    }


    public HashMap<Integer, String> getComponents() {
        return components;
    }
}
