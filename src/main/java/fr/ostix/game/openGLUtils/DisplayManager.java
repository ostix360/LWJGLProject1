package fr.ostix.game.openGLUtils;

import fr.ostix.game.toolBox.Logger;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager {

    private static int width = 1080;
    private static int height = 720;
    private static final String title = "Projet 1";


    private static long window;

    public static void createDisplay() {
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

        if (!glfwInit()) throw new RuntimeException("Unable/Failed to Initialize GLFW");

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE);
        //  glfwWindowHint(GLFW_FLOATING,GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if (window == NULL) {
            glfwTerminate();
            Logger.err("Failed to create GLFW" + glfwGetClipboardString(window));
            throw new RuntimeException("Unable/Failed to Create GLFW Window");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);

            glfwGetWindowSize(window, width, height);
            DisplayManager.width = width.get();
            DisplayManager.height = height.get();
        }


        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert vidMode != null;
        glfwSetWindowPos(window,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2
        );

        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        if (GL.getCapabilities().OpenGL46) //LOGGER.info("OpenGL 46 is available");

            GL11.glViewport(0, 0, width, height);

        glfwSwapInterval(1);
    }

    public static void updateDisplay() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);

            glfwGetWindowSize(window, width, height);
            DisplayManager.width = width.get();
            DisplayManager.height = height.get();
        }
        GL11.glViewport(0, 0, width, height);
        glfwSwapBuffers(window);

        //Logger.log(width + " " + height);
    }


    public static void closeDisplay() {
        Logger.log("Exit");
        Callbacks.glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
