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
    private static final Map<FontType, List<GUIText>> tempGuisTexts = new HashMap<>();
    private static Loader loader = Loader.INSTANCE;
    private final FontRenderer renderer;

    public MasterFont(Loader theLoader) {
        renderer = new FontRenderer();
        loader = theLoader;
    }

    public static void add(GUIText text) {
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        MeshModel vao = loader.loadFontToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<GUIText> textBatch = guisTexts.computeIfAbsent(font, k -> new ArrayList<>());
        textBatch.add(text);
    }

    public static void addTempFont(GUIText text) {
        FontType font = text.getFont();
        if (text.getVao() == null || text.getVertexCount() == 0) {
            TextMeshData data = font.loadText(text);
            MeshModel vao = loader.loadFontToVAO(data.getVertexPositions(), data.getTextureCoords());
            text.setMeshInfo(vao, data.getVertexCount());
        }
        List<GUIText> textBatch = tempGuisTexts.computeIfAbsent(font, k -> new ArrayList<>());
        textBatch.add(text);
    }

    public void render() {
        addTempText();
        renderer.render(guisTexts);
        removeTempGuis();
    }

    private void addTempText() {
        guisTexts.putAll(tempGuisTexts);
    }

    private void removeTempGuis() {
        for (FontType font : tempGuisTexts.keySet()) {
            List<GUIText> tempTexts = tempGuisTexts.get(font);
            for (GUIText text : tempTexts) {
                List<GUIText> texts = guisTexts.get(text.getFont());
                texts.remove(text);
                tempTexts.remove(text);
                if (texts.isEmpty()) {
                    guisTexts.remove(text.getFont());
                }
                if (tempTexts.isEmpty()) {
                    tempGuisTexts.remove(text.getFont());
                    break;
                }
            }
        }
    }

    public static void remove(GUIText text) {
        List<GUIText> texts = guisTexts.get(text.getFont());
        texts.remove(text);
        if (texts.isEmpty()) {
            guisTexts.remove(text.getFont());
        }

    }

    public void cleanUp() {
        for (FontType font : guisTexts.keySet()) {
            List<GUIText> texts = guisTexts.get(font);
            for (GUIText text : texts) {
                text.remove();
            }
            texts.clear();
        }
        tempGuisTexts.clear();
        guisTexts.clear();
        renderer.cleanUp();
    }
}
