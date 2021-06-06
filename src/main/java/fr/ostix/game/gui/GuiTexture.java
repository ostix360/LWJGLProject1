package fr.ostix.game.gui;

import fr.ostix.game.graphics.textures.Texture;
import org.joml.Vector2f;

public class GuiTexture {
    private final Vector2f position;
    private final Vector2f scale;
    private final Texture texture;

    public GuiTexture(Texture texture, Vector2f position, Vector2f scale) {
        this.position = convertCoordinates(position);
        this.scale = convertCoordinates(scale);
        this.texture = texture;
    }

    private Vector2f convertCoordinates(Vector2f value){
        return new Vector2f(value.x()/1980*2-1,value.y()/1080*2-1);
    }
    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public Texture getTexture() {
        return texture;
    }
}
