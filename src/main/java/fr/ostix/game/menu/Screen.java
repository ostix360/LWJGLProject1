package fr.ostix.game.menu;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.menu.component.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class Screen {
    private final List<Component> components = new ArrayList<>();
    protected Loader loader;
    protected String title;
    protected MasterGui masterGui;
    protected ResourcePack pack;

    public Screen(String title) {
        this.title = title;
    }

    public void init(Loader loader, MasterGui masterGui, ResourcePack pack) {
        this.loader = loader;
        this.masterGui = masterGui;
        this.pack = pack;
    }

    protected void addComponent(Component c) {
        components.add(c);
        c.init();
    }

    protected void removeComponent(Component c) {
        components.remove(c);
        c.cleanUp();
    }

    public void update() {
        for (Component c : components) {
            c.update();
        }
    }


    public void cleanUp() {
        for (Component c : components) {
            c.cleanUp();
        }
    }

    public String getTitle() {
        return title;
    }
}
