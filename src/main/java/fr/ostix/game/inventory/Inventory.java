package fr.ostix.game.inventory;

import fr.ostix.game.gui.*;
import fr.ostix.game.menu.*;

public abstract class Inventory extends Screen {
    private GuiTexture backGround;
    private boolean isOpen = false;


    public Inventory(String title) {
        super(title);
    }

    public void open() {
        MasterGui.addGui(backGround);
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
        isOpen = false;
    }
}
