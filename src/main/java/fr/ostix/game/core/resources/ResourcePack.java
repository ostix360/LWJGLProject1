package fr.ostix.game.core.resources;

import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.animated.animation.animation.Animation;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.model.Texture;

import java.util.HashMap;

public class ResourcePack {
    private final HashMap<String, Texture> textures;
    private final HashMap<String, SoundSource> sounds;
    private final HashMap<String, Model> models;
    private final HashMap<String,AnimatedModel> animatedModels;
    private final HashMap<AnimatedModel,HashMap<String, Animation>> animations;

    public ResourcePack(HashMap<String, Texture> textures, HashMap<String, SoundSource> sounds,
                        HashMap<String, Model> models, HashMap<String, AnimatedModel> animatedModelByName,
                        HashMap<AnimatedModel, HashMap<String, Animation>> animations) {
        this.textures = textures;
        this.sounds = sounds;
        this.models = models;
        this.animatedModels = animatedModelByName;
        this.animations = animations;
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

    public HashMap<AnimatedModel, HashMap<String, Animation>> getAnimationByName() {
        return animations;
    }

    public HashMap<String, AnimatedModel> getAnimatedModelByName() {
        return animatedModels;
    }


}
