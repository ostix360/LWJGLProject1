package fr.ostix.game.openGLUtils;

import org.lwjgl.opengl.GL11;

public class OpenGlUtils {

    public static void clearGL() {
        GL11.glClearColor(1, 1, 1, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }
}
