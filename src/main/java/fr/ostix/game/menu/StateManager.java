package fr.ostix.game.menu;

import fr.ostix.game.core.loader.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.graphics.*;
import fr.ostix.game.graphics.particles.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.toolBox.*;


public class StateManager {

    private final Screen[] screens;
    private final Loader loader;
    private final WorldState world;
    private ResourcePack pack;
    private MainMenu mainMenu;

    public StateManager(Loader loader) {
        this.loader = loader;
        screens = new Screen[2];
        world = new WorldState();
    }

    public void init(MasterGui masterGui) {
        LoaderMenu loaderMenu = new LoaderMenu();
        loaderMenu.init(loader, masterGui);
        loaderMenu.cleanUp();
        pack = loaderMenu.getPack();
        mainMenu = new MainMenu();
        mainMenu.init();
        screens[0] = mainMenu;
    }

    public int update() {
        if (!world.isWorldInitialized()) {
            world.init(pack);
            screens[1] = world;
            MasterParticle.init(loader, MasterRenderer.getProjectionMatrix());
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
}
