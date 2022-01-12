package fr.ostix.game.entity.entities;

import fr.ostix.game.core.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.inventory.*;
import org.joml.*;
import org.lwjgl.glfw.*;

public class Shop extends Entity {

    private final ShopInventory inventory;

    public Shop(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
        inventory = new ShopInventory();
        this.canInteract = true;
        this.addInteractionListener(() -> {
            if (Input.keys[GLFW.GLFW_KEY_E]) {
                if (inventory.isOpen()) {
                    inventory.close();
                } else {
                    inventory.open();
                }
            }
        });
    }


    @Override
    public void update() {
        super.update();
        inventory.update();
    }

    public ShopInventory getInventory() {
        return inventory;
    }


}
