package fr.ostix.game.inventory;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.inventoryEvent.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.menu.*;
import org.joml.*;

public abstract class Inventory extends Screen {
    private final GuiTexture backGround;
    private boolean isOpen = false;


    public Inventory(String title) {
        super(title);
        this.backGround = new GuiTexture(ResourcePack.getTextureByName().get("inventory").getID(),
                new Vector2f(0), new Vector2f(1920,
                1080));
        init();
    }

    public void open() {
        MasterGui.addGui(backGround);
        EventManager.getInstance().callEvent(new InventoryOpenEvent(1, this));
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void update() {
        if (isOpen) {
            this.render();
        }
    }

    protected void render() {
    }


    public void close() {
        MasterGui.removeGui(backGround);
        EventManager.getInstance().callEvent(new InventoryCloseEvent(1, this));
        isOpen = false;
    }
}
