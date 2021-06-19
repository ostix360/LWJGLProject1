package fr.ostix.game.graphics.font.meshCreator;

import fr.ostix.game.graphics.textures.TextureLoader;

public class FontType {

    private final TextureLoader textureLoaderAtlas;
    private final TextMeshCreator loader;


    public FontType(TextureLoader textureLoaderAtlas, String fontFile) {
        this.textureLoaderAtlas = textureLoaderAtlas;
        this.loader = new TextMeshCreator(fontFile);
    }

    public TextureLoader getTextureAtlas() {
        return textureLoaderAtlas;
    }

    public TextMeshData loadText(GUIText text) {
        return loader.createTextMesh(text);
    }

}
