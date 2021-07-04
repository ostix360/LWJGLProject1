package fr.ostix.game.inventory;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.menu.Screen;
import fr.ostix.game.menu.component.Button;
import fr.ostix.game.toolBox.Logger;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import org.joml.Vector2f;

public class Inventory extends Screen {

    private GuiTexture backGround;
    private boolean isOpen = false;
    private ItemTab recipeTab;
    private Button right_button;

    public Inventory(String title) {
        super(title);
        this.title = title;
    }

    public void init(Loader loader, ResourcePack pack, MasterGui masterGui) {
        this.loader = loader;
        this.pack = pack;
        this.masterGui = masterGui;
        this.backGround = new GuiTexture(ResourcePack.getTextureByName().get("inventory").getID(),
                new Vector2f(0), new Vector2f(DisplayManager.getWidth(),
                DisplayManager.getHeight()));
        this.right_button = new Button(1030, 85, 50, 610,
                ResourcePack.getTextureByName().get("right_button").getID());
        recipeTab = ItemTab.newEmptyTab("RecipeTab", 20);
        super.init(loader, masterGui, pack);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        MasterGui.addGui(backGround);
        this.addComponent(right_button);
        recipeTab.startRendering(this.masterGui);
        isOpen = true;
    }


    public void close() {
        MasterGui.removeGui(backGround);
        this.removeComponent(right_button);
        this.recipeTab.stopRendering(this.masterGui);
        isOpen = false;
    }

    @Override
    public void update() {
        super.update();
        if (right_button.isPressed()) Logger.log("right button");
        recipeTab.update();
    }


}
