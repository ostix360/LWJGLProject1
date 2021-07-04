package fr.ostix.game.menu;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.toolBox.Logger;


public class StateManager {

    private final Screen[] screens;
    private final Loader loader;
    private final WorldState world;
    private ResourcePack pack;
    private MasterGui masterGui;
    private MainMenu mainMenu;

    public StateManager(Loader loader) {
        this.loader = loader;
        screens = new Screen[2];
        world = new WorldState();
    }

    public void init(MasterGui masterGui) {
        this.masterGui = masterGui;
        LoaderMenu loaderMenu = new LoaderMenu();
        loaderMenu.init(loader, masterGui);
        loaderMenu.cleanUp();
        pack = loaderMenu.getPack();
        mainMenu = new MainMenu();
        mainMenu.init(loader, masterGui, pack);
        screens[0] = mainMenu;
    }

    public int update() {
        if (!world.isWorldInitialized()) {
            world.init(loader, masterGui, pack);
            screens[1] = world;
            Logger.log("World is Loaded");
        }
        if (mainMenu.startWorld) {
            return 1;
        }
        return 0;
    }

    public Screen getCurrentState(int id) {
        return screens[id];
    }

    public void cleanUp() {
        for (Screen s : screens) {
            s.cleanUp();
        }
    }

    public ResourcePack getPack() {
        return pack;
    }
}
