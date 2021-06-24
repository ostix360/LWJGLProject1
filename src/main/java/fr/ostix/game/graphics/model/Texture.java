package fr.ostix.game.graphics.model;

import fr.ostix.game.core.resources.TextureProperties;
import fr.ostix.game.graphics.textures.TextureLoader;
import org.lwjgl.opengl.GL11;

public class Texture {
    private final int textureID;
    private final int size;
    private final TextureProperties properties;

    public Texture(TextureLoader textureID, TextureProperties properties) {
        this.textureID = textureID.getId();
        this.properties = properties;
        this.size = textureID.getWidth();
    }

    public static void unBindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

    }

    public boolean useFakeLighting() {
        return properties.useFakeLighting();
    }

    public boolean isInverseNormal() {
        return properties.isInverseNormal();
    }

    public boolean isTransparency() {
        return properties.isTransparency();
    }

    public int getNormalMapID() {
        return properties.getNormalMapID();
    }

    public int getNumbersOfRows() {
        return properties.getNumbersOfRows();
    }

    public int getSpecularMap() {
        return properties.getSpecularMap();
    }

    public boolean hasSpecularMap() {
        return properties.hasSpecularMap();
    }

    public float getShineDamper() {
        return properties.getShineDamper();
    }

    public float getReflectivity() {
        return properties.getReflectivity();
    }

    public int getTextureID() {
        return textureID;
    }

    public void bindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }

    public int getSize() {
        return size;
    }
}
