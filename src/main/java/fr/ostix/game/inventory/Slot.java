package fr.ostix.game.inventory;

import fr.ostix.game.core.Input;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.items.ItemStack;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import org.joml.Vector2f;

public class Slot {
    private final float x, y;
    private final float size;
    private final GuiTexture texture;
    private final GuiTexture itemDescriptionMenu;
    private boolean isEmpty;
    private ItemStack stack;
    private boolean isIn;

    public Slot(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
        texture = new GuiTexture(ResourcePack.getTextureByName().get("slot").getID(),
                new Vector2f(x, y), new Vector2f(size * 1.23f, size));
        texture.setLayer(new Color(0.3f, 0.3f, 0.3f, 0.3f));
        itemDescriptionMenu = new GuiTexture(ResourcePack.getTextureByName().get("itemDescription").getID(),
                new Vector2f(500, 200), new Vector2f(400, 500));
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
        float mX = (float) Input.getMouseX() / DisplayManager.getWidth() * 1920;
        float mY = (float) Input.getMouseY() / DisplayManager.getHeight() * 1080;

        isIn = mX >= this.x && mY >= this.y &&
                mX < (this.x + this.size) && mY < (this.y + this.size);
        texture.hasLayer(isIn);
    }

    public void render() {
        if (isIn && !isEmpty()) {
            MasterGui.addTempGui(itemDescriptionMenu);
            MasterFont.addTempFont(stack.getItem().getItemDescription());
        }
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
