package fr.ostix.game.graphics.model;

import fr.ostix.game.graphics.textures.Texture;
import org.lwjgl.opengl.GL11;

public class TextureModel {
    private final int textureID;
    private int normalMapID;
    private int specularMap;

    private float shineDamper = 0;
    private float reflectivity = 0;

    private int numbersOfRows = 1;

    private boolean isTransparency = false;
    private boolean useFakeLighting = false;
    private boolean isInverseNormal = false;
    private boolean hasSpecularMap = false;

    public TextureModel(Texture texture) {
        this.textureID = texture.getId();
    }


    public boolean useFakeLighting() {
        return useFakeLighting;
    }

    public TextureModel setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
        return this;
    }

    public boolean isInverseNormal() {
        return isInverseNormal;
    }

    public TextureModel setInverseNormal(boolean value) {
        this.isInverseNormal = value;
        return this;
    }

    public boolean isTransparency() {
        return isTransparency;
    }

    public TextureModel setTransparency(boolean transparency) {
        isTransparency = transparency;
        return this;
    }

    public TextureModel setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
        return this;
    }

    public TextureModel setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
        return this;
    }

    public int getNormalMapID() {
        return normalMapID;
    }

    public TextureModel setNormalMapID(int normalMapID) {
        this.normalMapID = normalMapID;
        return this;
    }

    public int getNumbersOfRows() {
        return numbersOfRows;
    }


    public int getSpecularMap() {
        return specularMap;
    }

    public TextureModel setExtraInfoMap(int specularMap) {
        this.specularMap = specularMap;
        this.hasSpecularMap = true;
        return this;
    }

    public boolean hasSpecularMap() {
        return hasSpecularMap;
    }


    public float getShineDamper() {
        return shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }


    public void bindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureID);
    }

    public static void unBindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}
