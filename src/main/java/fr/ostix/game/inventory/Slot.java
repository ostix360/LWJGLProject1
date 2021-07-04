package fr.ostix.game.inventory;

import fr.ostix.game.core.Input;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.items.ItemStack;
import fr.ostix.game.toolBox.Color;
import org.joml.Vector2f;

public class Slot {
    private final float x, y;
    private final float size;
    private final GuiTexture texture;
    private boolean isEmpty;
    private ItemStack stack;

    public Slot(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
        texture = new GuiTexture(ResourcePack.getTextureByName().get("slot").getID(),
                new Vector2f(x, y), new Vector2f(size));
        texture.setLayer(new Color(0.3f, 0.3f, 0.3f, 0.3f));
        this.stack = new ItemStack(null, 0);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }


    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public void update() {
        float mX = (float) Input.getMouseX();
        float mY = (float) Input.getMouseY();

        boolean isIn = mX >= this.x && mY >= this.y &&
                mX < (this.x + this.size) && mY < (this.y + this.size);
        texture.hasLayer(isIn);

    }

    public void startRendering(MasterGui masterGui) {
        if (!this.getStack().isEmpty()) {
            stack.getItem().startRendering(masterGui, this.x + 5, this.y + 5);
        }
        MasterGui.addGui(texture);
    }

    public void stopRendering(MasterGui masterGui) {
        if (!this.getStack().isEmpty()) {
            stack.getItem().stopRendering(masterGui);
        }
        MasterGui.removeGui(texture);
    }
}
