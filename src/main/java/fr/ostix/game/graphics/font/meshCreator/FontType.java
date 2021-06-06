package fr.ostix.game.graphics.font.meshCreator;

import fr.ostix.game.graphics.textures.Texture;

public class FontType {

	private final Texture textureAtlas;
	private final TextMeshCreator loader;


	public FontType(Texture textureAtlas, String fontFile) {
		this.textureAtlas = textureAtlas;
		this.loader = new TextMeshCreator(fontFile);
	}

	public Texture getTextureAtlas() {
		return textureAtlas;
	}

	public TextMeshData loadText(GUIText text) {
		return loader.createTextMesh(text);
	}

}
