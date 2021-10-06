package fr.ostix.game.core.resources;

public class TextureProperties {

    private final boolean additive;
    private final boolean affectedByLighting;

    private final int normalMapID;
    private final int specularMap;

    private final float shineDamper;
    private final float reflectivity;

    private final int numbersOfRows;

    private final boolean isTransparency;
    private final boolean useFakeLighting;
    private final boolean isInverseNormal;

    public TextureProperties(boolean additive, boolean affectedByLighting, int normalMapID, int specularMap, float shineDamper, float reflectivity, int numbersOfRows, boolean isTransparency, boolean useFakeLighting, boolean isInverseNormal) {
        this.additive = additive;
        this.affectedByLighting = affectedByLighting;
        this.normalMapID = normalMapID;
        this.specularMap = specularMap;
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.numbersOfRows = numbersOfRows;
        this.isTransparency = isTransparency;
        this.useFakeLighting = useFakeLighting;
        this.isInverseNormal = isInverseNormal;
    }

    public boolean isAdditive() {
        return additive;
    }

    public boolean isAffectedByLighting() {
        return affectedByLighting;
    }


    public int getNormalMapID() {
        return normalMapID;
    }

    public int getSpecularMap() {
        return specularMap;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public int getNumbersOfRows() {
        return numbersOfRows;
    }

    public boolean isTransparency() {
        return isTransparency;
    }

    public boolean useFakeLighting() {
        return useFakeLighting;
    }

    public boolean isInverseNormal() {
        return isInverseNormal;
    }

    public boolean hasSpecularMap() {
        return specularMap != 0;
    }
}
