package fr.ostix.game.menu;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.menu.component.Button;

public class MainMenu extends Screen {

    public boolean startWorld = false;
    private Button start;

    public MainMenu() {
        super("Main Menu");
    }

    @Override
    public void init(Loader loader, MasterGui masterGui, ResourcePack pack) {
        super.init(loader, masterGui, pack);

        start = new Button((float) 1920 / 2 - 125,
                (float) 1080 / 2 - 200, 250, 125, ResourcePack.getTextureByName().get("startButton").getID());
        this.addComponent(start);

    }

    @Override
    public void update() {
        super.update();
        if (start.isPressed()) {
            startWorld = true;
            this.removeComponent(start);
        }
    }
}
