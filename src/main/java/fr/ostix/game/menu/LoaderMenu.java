package fr.ostix.game.menu;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.menu.component.Button;
import fr.ostix.game.openGLUtils.DisplayManager;

public class LoaderMenu extends Screen {

    private Button start;

    public LoaderMenu() {
        super("Loader Menu");
    }

    @Override
    public void init(Loader loader) {
        super.init(loader);

        start = new Button((float) DisplayManager.getWidth() / 2 - 75, (float) DisplayManager.getHeight() / 2 - 180, 150, 50, this.loader.loadTexture("/menu/start"));
        this.addComponent(start);
    }

    @Override
    public void update() {
        start.update();
        if (start.isPressed()) {
            System.out.println("start");
        }
    }
}
