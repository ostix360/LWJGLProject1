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
        enduranceBar = new GuiTexture(ResourcePack.getTextureByName().get("enduranceBar").getID(), new Vector2f(975, 955), new Vector2f(590, 135));
        enduranceTexture = ResourcePack.getTextureByName().get("endurance").getID();
    }


    @Override
    public void update() {
        super.update();
    }

    public void render() {
        for (int i = 0; i < player.getHealth(); i++) {
            MasterGui.addTempGui(new GuiTexture(heartTexture, new Vector2f(250 + i * 60, 970), new Vector2f(100f)));
        }
        float percent = (player.getSprintTime()
                / (60)) * 450;
        MasterGui.addTempGui(new GuiTexture(enduranceTexture, new Vector2f(1110, 985), new Vector2f(percent, 60)));
        MasterGui.addTempGui(enduranceBar);
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }
}
