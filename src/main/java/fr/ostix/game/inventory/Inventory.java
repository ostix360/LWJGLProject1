package fr.ostix.game.inventory;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.menu.*;
import org.joml.*;

public abstract class Inventory extends Screen {
    private final GuiTexture backGround;
    private boolean isOpen = false;
    private final InventoryListener invListener;


    public Inventory(String title) {
        super(title);
        this.backGround = new GuiTexture(ResourcePack.getTextureByName().get("inventory").getID(),
                new Vector2f(0), new Vector2f(1920,
                1080));
        this.invListener = WorldState.getInventoryListener();
        init();
    }

    public void open() {
        MasterGui.addGui(backGround);
        invListener.onOpen(new InventoryEvent(1, this));
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
        invListener.onClose(new InventoryEvent(0, this));
        isOpen = false;
    }
}
