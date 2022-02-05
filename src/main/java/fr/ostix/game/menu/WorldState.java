package fr.ostix.game.menu;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.inventory.*;
import fr.ostix.game.world.*;

import static org.lwjgl.glfw.GLFW.*;

public class WorldState extends Screen {
    private final World world;
    private static InventoryListener il;
    public PlayerInventory playerInventory;
    private final InGameMenu hotBar;
    private boolean worldInitialized = false;

    private boolean worldCanBeUpdated = true;
    private boolean openInventory = false;
    private Inventory currentInventory;

    public WorldState() {
        super("World");
        world = new World();
        playerInventory = new PlayerInventory("Player Inventory");
        hotBar = new InGameMenu();
        il = new InventoryListener() {
            @Override
            public void onOpen(InventoryEvent e) {
                worldCanBeUpdated = false;
                currentInventory = e.getInv();
            }

            @Override
            public void onClose(InventoryEvent e) {
                worldCanBeUpdated = true;
                currentInventory = null;
            }
        };
    }

    public boolean isWorldInitialized() {
        return worldInitialized;
    }

    public static InventoryListener getInventoryListener() {
        return il;
    }

    public void init(ResourcePack pack) {
        super.init();
        world.initWorld(pack);
        playerInventory.init();
        hotBar.init(world.getPlayer());
        worldInitialized = world.isInit();

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

    @Override
    public void update() {
        super.update();
        checkInputs();
        if (openInventory) {
            if (!playerInventory.isOpen()) {
                playerInventory.open();
            }
            playerInventory.update();
        } else if (playerInventory.isOpen()) {
            playerInventory.close();
        } else if (worldCanBeUpdated) {
            world.update();
        } else if (currentInventory != null) {
            currentInventory.update();
        } else {
            System.err.println("Problem during the choice of the thing that need to be update");
        }

    }

    private void checkInputs() {
        if (Input.keyPressed(GLFW_KEY_TAB)) {
            openInventory = !openInventory;
            worldCanBeUpdated = !openInventory;
            if (currentInventory != null) currentInventory.close();
            currentInventory = null;
        }

        if (Input.keys[GLFW_KEY_ESCAPE]) {
            openInventory = false;
            worldCanBeUpdated = true;
            if (currentInventory != null) currentInventory.close();
            currentInventory = null;
        }
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        world.cleanUp();
    }
}


