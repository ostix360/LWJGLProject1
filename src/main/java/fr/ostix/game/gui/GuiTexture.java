package fr.ostix.game.gui;

import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import org.joml.Vector2f;

public class GuiTexture {
    private final Vector2f position;
    private final int texture;
    private Vector2f scale;
    private Color layer = new Color(0, 0, 0, 0);
    private boolean hasLayer = false;

    /**
     * @param position correspond a une pourcentage au niveau de la fenetre sur 1000
     */
    public GuiTexture(int texture, Vector2f position, Vector2f scale) {
        this.position = convertPercentToPos(position, scale);
        this.scale = convertScale(scale);
        this.texture = texture;
    }

    private Vector2f convertPercentToPos(Vector2f position, Vector2f scale) {
        Vector2f newPos = new Vector2f(position.x() * DisplayManager.getWidth() / 1920,
                position.y() * DisplayManager.getHeight() / 1080);
        Vector2f newScale = new Vector2f(scale.x() * DisplayManager.getWidth() / 1920,
                scale.y() * DisplayManager.getHeight() / 1080);
        return convertPos(newPos, newScale);
    }

    private Vector2f convertPos(Vector2f pos, Vector2f scale) {
        return convertCoordinates(new Vector2f(pos.x() + scale.x() / 2, pos.y() + scale.y() / 2));
    }

    private Vector2f convertCoordinates(Vector2f value) {
        return new Vector2f(value.x() / DisplayManager.getWidth() * 2 - 1, 1 - (value.y() / DisplayManager.getHeight() * 2));
    }

    private Vector2f convertScale(Vector2f value) {
        return new Vector2f(value.x() / 1920, value.y() / 1080);
    }

    public void setScale(Vector2f scale) {
        this.scale = convertScale(scale);
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public int getTexture() {
        return texture;
    }

    public boolean hasLayer() {
        return hasLayer;
    }

    public void hasLayer(boolean value) {
        hasLayer = value;
    }

    public Color getLayer() {
        if (hasLayer) {
            return layer;
        } else {
            return new Color(0, 0, 0, 0);
        }
    }

    public void setLayer(Color layer) {
        this.layer = layer;
        hasLayer = true;
    }
}
