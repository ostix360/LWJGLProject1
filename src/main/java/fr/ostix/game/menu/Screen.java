package fr.ostix.game.menu;

import fr.ostix.game.menu.component.Component;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    protected final List<Component> components = new ArrayList<>();
    protected String title;

    public Screen(String title) {
        this.title = title;
    }

    public void init() {
        for (Component c : components) {
            c.init();
        }
    }


    public void cleanUp() {
        for (Component c : components) {
            c.cleanUp();
        }
    }
}
