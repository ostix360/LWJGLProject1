package fr.ostix.game.items;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.toolBox.Color;
import org.joml.Vector2f;

public class Item {
    private final int id;
    private final String name;
    private final String description;
    private final int texture;
    private GuiTexture gui;
    private final GUIText itemDescription;

    public Item(int id, String name, String description, String textureName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.texture = ResourcePack.getTextureByName().get(textureName).getID();
        this.itemDescription = new GUIText(description, 1f, Game.gameFont,
                new Vector2f(570, 260f), 300f, false);
        this.itemDescription.setColour(Color.MAGENTA);
    }

    public void onItemUse() {
    }

    public GUIText getItemDescription() {
        return itemDescription;
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
        gui = new GuiTexture(texture, new Vector2f(x, y), new Vector2f(130 * 1.23f, 130));
        MasterGui.addGui(gui);
    }

    public void stopRendering(MasterGui masterGui) {
        MasterGui.removeGui(gui);
    }
}
