package fr.ostix.game.core.loader;

import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.model.Texture;

public class ResourceLoader {


    public static Model loadTexturedModel(String path, Texture texture, Loader loader) {
        if (texture == null) {
            new IllegalArgumentException("texture for the model " + path + "is not available ");
        }
        assert texture != null;
        return new Model(LoadMeshModel.loadModel(path, loader),
                texture);
    }
}
