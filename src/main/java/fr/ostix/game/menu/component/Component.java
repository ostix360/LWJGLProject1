package fr.ostix.game.menu.component;

import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;

public class Component {

    protected float x, y;
    protected float width, height;

    protected GuiTexture texture;


    public Component(float x, float y, float width, float height, GuiTexture texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
    }

    public void init() {
        MasterGui.addGui(texture);
    }

    public void cleanUp() {
        MasterGui.removeGui(texture);
    }
}
