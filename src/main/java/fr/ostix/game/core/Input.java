package fr.ostix.game.core;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.toolBox.*;
import org.lwjgl.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private static final DoubleBuffer MOUSE_X = BufferUtils.createDoubleBuffer(1);
    public static boolean[] keysMouse = new boolean[65535];
    private static final boolean[] keysRealesed = new boolean[65535];
    private static final DoubleBuffer MOUSE_Y = BufferUtils.createDoubleBuffer(1);
    public static boolean[] keys = new boolean[65535];
    private static double mouseX;
    private static double mouseY;
    public static float mouseDY;
    public static float mouseDX;
    public static float mouseDWhell;
    private static float beforePositionX;
    private static float beforePositionY;
    public static boolean[] keysMousePressed = new boolean[65535];



    public static void init(long window) {
        GLFW.glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == -1) {
                    Logger.err("Error this key ( " + scancode + ") is unknown");
                    return;
                }
                keys[key] = action != GLFW_RELEASE;

                if (action == GLFW_PRESS) {
                    EventManager.getInstance().callEvent(new KeyEvent(0, key));
                    EventManager.getInstance().callEvent(new KeyPressedEvent(0, key));
                }
                if (action == GLFW_RELEASE) {
                    EventManager.getInstance().callEvent(new KeyEvent(0, key));
                    EventManager.getInstance().callEvent(new KeyReleasedEvent(0, key));
                } else {
                    EventManager.getInstance().callEvent(new KeyEvent(0, key));
                    EventManager.getInstance().callEvent(new KeyMaintainedEvent(0, key));
                }
            }
        });

        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                keysMouse[button] = action != GLFW_RELEASE;
            }
        });

        glfwSetScrollCallback(window, (w, xoffset, yoffset) -> mouseDWhell = (float) (yoffset - xoffset));
    }

    public static void updateInput(long window) {
        mouseDY = 0;
        mouseDX = 0;
        mouseDWhell = 0;

        glfwGetCursorPos(window, MOUSE_X, MOUSE_Y);
        mouseX = MOUSE_X.get();
        mouseY = MOUSE_Y.get();
        mouseDX = (float) mouseX - beforePositionX;
        mouseDY = (float) mouseY - beforePositionY;


        MOUSE_X.flip();
        MOUSE_Y.flip();

        beforePositionX = (float) MOUSE_X.get();
        beforePositionY = (float) MOUSE_Y.get();

        MOUSE_X.flip();
        MOUSE_Y.flip();

    }

    public static boolean keyPressed(int key) {
        if (keys[key] && !keysRealesed[key]) {
            keysRealesed[key] = true;
            return true;
        }
        keysRealesed[key] = keys[key];
        return false;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static float getMouseDWhell() {
        float value = mouseDWhell;
        mouseDWhell = 0;
        return value;
    }
}
