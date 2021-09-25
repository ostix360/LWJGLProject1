package fr.ostix.game.items;

import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import org.joml.Vector2f;

public class Item {
    private final int id;
    private final String name;
    private final String description;
    private final int texture;
    private GuiTexture gui;

    public Item(int id, String name, String description, String textureName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.texture = ResourcePack.getTextureByName().get(textureName).getID();
    }

    public void onItemUse() {
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTexture() {
        return texture;
    }

    public void startRendering(MasterGui masterGui, float x, float y) {
        gui = new GuiTexture(texture, new Vector2f(x, y), new Vector2f(130, 130));
        MasterGui.addGui(gui);
    }

    public void stopRendering(MasterGui masterGui) {
        MasterGui.removeGui(gui);
    }
}
