package fr.ostix.game.menu;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.Player;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import org.joml.Vector2f;

public class InGameMenu extends Screen {
    private Player player;
    private int heartTexture;
    private GuiTexture enduranceBar;
    private int enduranceTexture;

    public InGameMenu() {
        super("My World");
    }

    public void init(Loader loader, MasterGui masterGui, ResourcePack pack, Player player) {
        super.init(loader, masterGui, pack);
        this.player = player;
        heartTexture = ResourcePack.getTextureByName().get("food").getID();
        enduranceBar = new GuiTexture(ResourcePack.getTextureByName().get("enduranceBar").getID(), new Vector2f(580, 650), new Vector2f(350, 85));
        enduranceTexture = ResourcePack.getTextureByName().get("endurance").getID();
    }


    @Override
    public void update() {
        super.update();
    }

    public void render() {
        for (int i = 0; i < player.getHealth(); i++) {
            MasterGui.addTempGui(new GuiTexture(heartTexture, new Vector2f(150 + i * 35, 670), new Vector2f(50f)));
        }
        float percent = ((float) player.getSprintTime()
                / 60) * 265;
        MasterGui.addTempGui(new GuiTexture(enduranceTexture, new Vector2f(660, 670), new Vector2f(percent, 35)));
        MasterGui.addTempGui(enduranceBar);
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }
}
