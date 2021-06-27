package fr.ostix.game.core.loader;

import fr.ostix.game.audio.AudioManager;
import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.core.loader.json.JsonUtils;
import fr.ostix.game.core.resources.AnimationResources;
import fr.ostix.game.core.resources.ModelResources;
import fr.ostix.game.core.resources.SoundResources;
import fr.ostix.game.core.resources.TextureResources;
import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.animated.animation.animation.Animation;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.model.Texture;
import fr.ostix.game.graphics.textures.TextureLoader;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import fr.ostix.game.toolBox.ToolDirectory;
import org.joml.Vector2f;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import static fr.ostix.game.toolBox.OpenGL.OpenGlUtils.clearGL;

public class ResourcePackLoader {

    private final Loader loader;
    private final String DATA = "data";
    private final HashMap<String, Texture> textureByName = new HashMap<>();
    private final HashMap<String, SoundSource> soundByName = new HashMap<>();
    private final HashMap<String, Model> modelByName = new HashMap<>();
    private final HashMap<String,AnimatedModel> animatedModelByName = new HashMap<>();
    private final HashMap<AnimatedModel,HashMap<String, Animation>> animationByName = new HashMap<>();

    private boolean isLoaded = false;

    public ResourcePackLoader(Loader loader) {
        this.loader = loader;
    }

    public void loadAllResource(MasterGui masterGui) {
        TextureLoader progress = loader.loadTexture("menu/loader/progress");
        Vector2f pos = new Vector2f(90, DisplayManager.getHeight() - 150);


        ProgressManager.ProgressBar resourcesBar = ProgressManager.addProgressBar("Loading All Resource", 4);
        clearGL();
        masterGui.render();
        resourcesBar.update("Loading Textures... ");
        loadAllTextures();
        DisplayManager.updateDisplay();


        GuiTexture bar = new GuiTexture(progress.getId(), pos,
                new Vector2f((float) (0.25 * (DisplayManager.getWidth() - 180)), 50));
        MasterGui.addGui(bar);
        clearGL();
        masterGui.render();
        MasterGui.removeGui(bar);
        resourcesBar.update("Loading Sounds... ");
        loadAllSounds();
        DisplayManager.updateDisplay();


        bar = new GuiTexture(progress.getId(), pos,
                new Vector2f((float) (0.5 * (DisplayManager.getWidth() - 180)), 50));
        MasterGui.addGui(bar);
        clearGL();

        masterGui.render();
        resourcesBar.update("Loading Models... ");
        loadAllModels();
        DisplayManager.updateDisplay();

        bar = new GuiTexture(progress.getId(), pos,
                new Vector2f((float) (0.75 * (DisplayManager.getWidth() - 180)), 50));
        MasterGui.addGui(bar);
        clearGL();

        masterGui.render();
        resourcesBar.update("Loading Animations... ");
        loadAllAnimations();
        DisplayManager.updateDisplay();

        bar = new GuiTexture(progress.getId(), pos,
                new Vector2f((float) (DisplayManager.getWidth() - 180), 50));
        MasterGui.addGui(bar);
        clearGL();

        masterGui.render();
        DisplayManager.updateDisplay();
        ProgressManager.remove(resourcesBar);
        isLoaded = true;
    }

    private void loadAllTextures() {
        File textureFolder = new File(ToolDirectory.RES_FOLDER + "/textures/", DATA);
        ProgressManager.ProgressBar texturesBar = ProgressManager.addProgressBar("Loading All Textures", Objects.requireNonNull(textureFolder.listFiles()).length);
        for (File currentFile : Objects.requireNonNull(textureFolder.listFiles())) {

            String json = JsonUtils.loadJson(currentFile.getAbsolutePath());
            if (json.isEmpty()) {
                new Exception("a json a texture is empty...");
            }
            TextureResources current = JsonUtils.gsonInstance().fromJson(json, TextureResources.class);
            if (current == null) {
                new NullPointerException("The file cannot " + currentFile.getName() + " be read");
            }
            assert current != null;
            String name = current.getName();
            texturesBar.update(name);
            Texture tex = new Texture(loader.loadTexture(current.getPath()), current.getTextureProperties());
            textureByName.put(name, tex);
        }
        ProgressManager.remove(texturesBar);
    }

    private void loadAllSounds() {
        File soundFolder = new File(ToolDirectory.RES_FOLDER + "/sounds/", DATA);
        ProgressManager.ProgressBar soundsBar = ProgressManager.addProgressBar("Loading All Sounds", Objects.requireNonNull(soundFolder.listFiles()).length);

        for (File currentFile : Objects.requireNonNull(soundFolder.listFiles())) {
            String json = JsonUtils.loadJson(currentFile.getAbsolutePath());
            if (json.isEmpty()) {
                new Exception("a json a sound is empty...");
            }
            SoundResources current = JsonUtils.gsonInstance().fromJson(json, SoundResources.class);
            if (current == null) {
                new NullPointerException("The file cannot " + currentFile.getName() + " be read");
            }
            assert current != null;
            String name = current.getName();
            soundsBar.update(name);
            SoundSource source = AudioManager.loadSound(current.getPath(), current.isAmbient());
            soundByName.put(name, source);
        }
        ProgressManager.remove(soundsBar);
    }

    private void loadAllModels() {
        File modelFolder = new File(ToolDirectory.RES_FOLDER + "/model/", DATA);
        ProgressManager.ProgressBar modelBar = ProgressManager.addProgressBar("Loading All Models", Objects.requireNonNull(modelFolder.listFiles()).length);

        for (File currentFile : Objects.requireNonNull(modelFolder.listFiles())) {
            String json = JsonUtils.loadJson(currentFile.getAbsolutePath());
            if (json.isEmpty()) {
                new Exception("a json a model is empty...");
            }
            ModelResources current = JsonUtils.gsonInstance().fromJson(json, ModelResources.class);
            if (current == null) {
                new NullPointerException("The file cannot " + currentFile.getName() + " be read");
            }
            assert current != null;
            String name = current.getName();
            modelBar.update(name);
            if (current.canAnimated()){
                AnimatedModel model = ResourceLoader.loadTexturedAnimatedModel(current.getPath(),textureByName.get(current.getTexture()), loader);
                animatedModelByName.put(name,model);
            }
//            Model model = ResourceLoader.loadTexturedModel(current.getPath(), textureByName.get(current.getTexture()), loader);
//            modelByName.put(name, model);
        }
        ProgressManager.remove(modelBar);
    }

    private void loadAllAnimations() {
        File modelFolder = new File(ToolDirectory.RES_FOLDER + "/animations/", DATA);
        ProgressManager.ProgressBar animationBar = ProgressManager.addProgressBar("Loading All Animations", Objects.requireNonNull(modelFolder.listFiles()).length);

        for (File currentFile : Objects.requireNonNull(modelFolder.listFiles())) {
            String json = JsonUtils.loadJson(currentFile.getAbsolutePath());
            if (json.isEmpty()) {
                new Exception("a json a model is empty...");
            }
            AnimationResources current = JsonUtils.gsonInstance().fromJson(json, AnimationResources.class);
            if (current == null) {
                new NullPointerException("The file cannot " + currentFile.getName() + " be read");
            }
            assert current != null;
            current.loadAnimation();
            animationBar.update(current.getAnimationName());
            optimizeAnimation(current);
        }
        ProgressManager.remove(animationBar);
    }

    private void optimizeAnimation(AnimationResources current) {
        AnimatedModel model = animatedModelByName.get(current.getModelName());
        HashMap<String,Animation> batch = animationByName.get(model);
        if (batch != null) {
            batch.put(current.getAnimationName(), current.getAnimation());
        }else{
            HashMap<String,Animation> newBatch = new HashMap<>();
            newBatch.put(current.getAnimationName(), current.getAnimation());
            animationByName.put(model, newBatch);
        }

    }

    public boolean isLoaded() {
        return isLoaded;
    }


    public HashMap<String, Texture> getTextureByName() {
        return textureByName;
    }

    public HashMap<String, SoundSource> getSoundByName() {
        return soundByName;
    }

    public HashMap<String, Model> getModelByName() {
        return modelByName;
    }

    public HashMap<String, AnimatedModel> getAnimatedModelByName() {
        return animatedModelByName;
    }

    public HashMap<AnimatedModel, HashMap<String, Animation>> getAnimationByName() {
        return animationByName;
    }
}
