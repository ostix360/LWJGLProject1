package fr.ostix.game.gui;

import fr.ostix.game.core.loader.Loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MasterGui {
    private static final List<GuiTexture> guis = new ArrayList<>();
    private final GuiRenderer renderer;


    public MasterGui(Loader loader) {
        renderer = new GuiRenderer(loader);
    }

    public static void addGui(GuiTexture... gui) {
        guis.addAll(Arrays.asList(gui));
    }

    public static void removeGui(GuiTexture... gui) {
        guis.removeAll(Arrays.asList(gui));
    }

    public void render() {
        renderer.render(guis);
    }

    public void removeAllGui() {
        guis.clear();
    }

    public void cleanUp() {
        removeAllGui();
        renderer.cleanUp();
    }
}
