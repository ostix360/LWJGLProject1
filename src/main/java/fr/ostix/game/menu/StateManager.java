package fr.ostix.game.menu;

import fr.ostix.game.core.loader.Loader;


public class StateManager {

    private final Screen[] screens;
    private final Loader loader;
    private boolean isTimeWorld;
    private LoaderMenu loaderMenu;

    public StateManager(Loader loader) {
        this.loader = loader;
        screens = new Screen[1];
    }

    public void init() {
        loaderMenu = new LoaderMenu();
        loaderMenu.init(loader);
        screens[0] = loaderMenu;
    }

    public int update() {
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
