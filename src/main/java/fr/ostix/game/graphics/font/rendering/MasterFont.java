package fr.ostix.game.graphics.font.rendering;



import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.graphics.font.meshCreator.FontType;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.meshCreator.TextMeshData;
import fr.ostix.game.graphics.model.MeshModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MasterFont {

    private static final Map<FontType, List<GUIText>> guisTexts = new HashMap<>();
    private static Loader loader;
    private static FontRenderer renderer;

    public static void init(Loader theLoader) {
        renderer = new FontRenderer();
        loader = theLoader;
    }

    public static void render() {
        renderer.render(guisTexts);
    }

    public static void loadText(GUIText text) {
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        MeshModel vao = loader.loadFontToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<GUIText> textBatch = guisTexts.computeIfAbsent(font, k -> new ArrayList<>());
        textBatch.add(text);
    }

    public static void remove(GUIText text) {
        List<GUIText> texts = guisTexts.get(text.getFont());
        texts.remove(text);
        if (texts.isEmpty()) {
            guisTexts.remove(text.getFont());
        }

    }

    public static void cleanUp() {
        renderer.cleanUp();
    }
}
