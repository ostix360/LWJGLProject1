package fr.ostix.game.menu;

import fr.ostix.game.core.*;
import fr.ostix.game.core.loader.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.inventory.*;
import fr.ostix.game.world.*;

import static org.lwjgl.glfw.GLFW.*;

public class WorldState extends Screen {
    private final World world;
    private final PlayerInventory playerInventory;
    private final InGameMenu hotBar;
    private boolean worldInitialized = false;

    private boolean worldCanBeUpdated = true;
    private boolean openInventory = false;
    private boolean eIsAlreadyPressed = false;

    public WorldState() {
        super("World");
        world = new World();
        playerInventory = new PlayerInventory("Player Inventory");
        hotBar = new InGameMenu();
    }

    public boolean isWorldInitialized() {
        return worldInitialized;
    }

    @Override
    public void init(Loader loader, MasterGui masterGui, ResourcePack pack) {
        super.init(loader, masterGui, pack);
        world.initWorld(loader, pack);
        playerInventory.init(loader, pack, masterGui);
        hotBar.init(loader, masterGui, pack, world.getPlayer());
        worldInitialized = world.isInit();
    }

    @Override
    public void update() {
        super.update();
        checkInputs();
        if (openInventory) {
            if (!playerInventory.isOpen()) {
                playerInventory.open();
            }
            playerInventory.update();
        }
        if (worldCanBeUpdated) {
            if (playerInventory.isOpen()) {
                playerInventory.close();
            }
            world.update();
        }

    }

    public void render() {
        world.render();
        if (!playerInventory.isOpen()) {
            hotBar.render();
        } else {
            playerInventory.render();
        }
    }


    public World getWorld() {
        return world;
    }

    private void checkInputs() {
        if (Input.keys[GLFW_KEY_TAB]) {
            if (!eIsAlreadyPressed) {
                openInventory = !openInventory;
                worldCanBeUpdated = !openInventory;
            }
            eIsAlreadyPressed = true;
        } else {
            eIsAlreadyPressed = false;
        }

        if (Input.keys[GLFW_KEY_ESCAPE]) {
            openInventory = false;
            worldCanBeUpdated = true;
        }
    }


    @Override
    public void cleanUp() {
        super.cleanUp();
        world.cleanUp();
    }
}
