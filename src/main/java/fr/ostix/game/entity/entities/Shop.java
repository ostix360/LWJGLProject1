package fr.ostix.game.entity.entities;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.inventory.*;
import fr.ostix.game.toolBox.*;
import org.joml.*;
import org.lwjgl.glfw.*;

public class Shop extends Entity {

    private final ShopInventory inventory;
    private final GuiTexture bgInteraction;
    private final GUIText interactionText;

    public Shop(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
        inventory = new ShopInventory();
        inventory.init();
        this.canInteract = true;
        bgInteraction = new GuiTexture(ResourcePack.getTextureByName().get("bgInteraction").getID(),
                new Vector2f(1920 / 2f - 60, 850), new Vector2f(120, 40));
        interactionText = new GUIText("Ouvrir", 5, Game.gameFont, new Vector2f(1920 / 2f - 50, 860),
                20, false);
        InteractionKeyListener keyListener = new InteractionKeyListener() {
            boolean isReleased = false;

            @Override
            public void onKeyPressed(KeyEvent e) {

                if (inventory.isOpen() && e.getKEY() == GLFW.GLFW_KEY_ESCAPE) {
                    inventory.close();
                }
            }

            @Override
            public void onKeyReleased(KeyEvent e) {
                isReleased = false;
            }
        };
        interactionText.setColour(Color.WHITE);
        this.addInteractionListener(new InteractionListener() {
            @Override
            public void playerIsNear() {

                MasterGui.addGui(bgInteraction);
                MasterFont.addTempFont(interactionText);
                Input.addKeyListener(keyListener);
                if (Input.keyPressed(GLFW.GLFW_KEY_E)) {
                    if (inventory.isOpen()) {
                        inventory.close();
                    } else {
                        inventory.open();
                    }
                }
            }

            @Override
            public void playerGone() {
                Input.removeKeyListener(keyListener);
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
