package fr.ostix.game.menu;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.loader.ResourcePackLoader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import org.joml.Vector2f;

public class LoaderMenu extends Screen {

    private ResourcePackLoader pack;
    private GuiTexture bar;


    public LoaderMenu() {
        super("Loader Menu");
    }

    public void init(Loader loader, MasterGui masterGui) {
        super.init(loader, masterGui, null);
        bar = new GuiTexture(loader.loadTexture("menu/loader/progressBar").getId(),
                new Vector2f(150, 1080 - 150),
                new Vector2f(1920 - 180, 80));
        MasterGui.addGui(bar);

        pack = new ResourcePackLoader(loader);
        pack.loadAllResource(masterGui);
        if (pack.isLoaded()) {
            this.masterGui.removeAllGui();
        }

    }

    public ResourcePack getPack() {
        return new ResourcePack(pack.getTextureByName(),
                pack.getSoundByName(), pack.getModelByName(),
                pack.getAnimatedModelByName(), pack.getAnimationByName(), pack.getComponentsByID());
    }

}
