package fr.ostix.game.menu.component;

import fr.ostix.game.graphics.textures.TextureLoader;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import org.joml.Vector2f;

public abstract class Component {

    protected float x, y;
    protected float width, height;

    protected GuiTexture texture;


    public Component(float x, float y, float width, float height, TextureLoader textureLoader) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = new GuiTexture(textureLoader, new Vector2f(x, y), new Vector2f(width, height));
    }

    public abstract void update();

    public void init() {
        MasterGui.addGui(texture);
    }

    public void cleanUp() {
        MasterGui.removeGui(texture);
    }
}
