package fr.ostix.game.menu;

import fr.ostix.game.core.Input;
import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.inventory.Inventory;
import fr.ostix.game.world.World;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class WorldState extends Screen {
    private final World world;
    private final Inventory inventory;
    private final InGameMenu hotBar;
    private boolean worldInitialized = false;

    private boolean worldCanBeUpdated = true;
    private boolean openInventory = false;
    private boolean eIsAlreadyPressed = false;

    public WorldState() {
        super("World");
        world = new World();
        inventory = new Inventory("Player Inventory");
        hotBar = new InGameMenu();
    }

    public boolean isWorldInitialized() {
        return worldInitialized;
    }

    @Override
    public void init(Loader loader, MasterGui masterGui, ResourcePack pack) {
        super.init(loader, masterGui, pack);
        world.initWorld(loader, pack);
        inventory.init(loader, pack, masterGui);
        hotBar.init(loader, masterGui, pack, world.getPlayer());
        worldInitialized = world.isInit();
    }

    @Override
    public void update() {
        super.update();
        checkInputs();
        if (openInventory) {
            if (!inventory.isOpen()) {
                inventory.open();
            }
            inventory.update();
        }
        if (worldCanBeUpdated) {
            if (inventory.isOpen()) {
                inventory.close();
            }
            world.update();
        }

    }

    public void render() {
        world.render();
        if (!inventory.isOpen()) {
            hotBar.render();
        }
    }


    public World getWorld() {
        return world;
    }

    private void checkInputs() {
        if (Input.keys[GLFW_KEY_E]) {
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
