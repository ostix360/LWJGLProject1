package fr.ostix.game.core.events.listener.keyMouselisteners;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.gui.*;
import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.menu.*;
import fr.ostix.game.menu.component.*;
import org.joml.*;
import org.lwjgl.glfw.*;

public class KeyMouseMenuListener implements KeyMouseListener {

    private final Screen gui;

    public KeyMouseMenuListener(Screen gui) {
        this.gui = gui;
    }

    @Override
    @EventHandler
    public void onKeyPress(KeyMousePressedEvent e) {
        if (e.getKEY() == GLFW.GLFW_MOUSE_BUTTON_1) {
            Button b = checkMouseIsInButton(e.getPosition());
            if (b != null) {
                EventManager.getInstance().callEvent(new ButtonEvent(b, 1));
            }
        }
    }

    @Override
    @EventHandler
    public void onKeyReleased(KeyMouseReleasedEvent e) {

    }

    @Override
    @EventHandler
    public void onKeyMaintained(KeyMouseMaintainedEvent e) {

    }

    private Button checkMouseIsInButton(Vector2f mousePos) {
        for (Component b : gui.getComponents()) {
            if (b instanceof Button) {
                if (((Button) b).mouseIn(mousePos)) {
                    return (Button) b;
                }
            }
        }
        return null;
    }
}
