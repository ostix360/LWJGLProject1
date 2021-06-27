package fr.ostix.game.core;

import fr.ostix.game.audio.AudioManager;
import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.font.meshCreator.FontType;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.menu.Screen;
import fr.ostix.game.menu.StateManager;
import fr.ostix.game.toolBox.Logger;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import fr.ostix.game.toolBox.OpenGL.OpenGlUtils;
import fr.ostix.game.world.World;
import org.lwjgl.openal.AL11;

import static org.lwjgl.glfw.GLFW.*;


public class Game extends Thread {


    public static FontType gameFont;

    private boolean running = false;

    private final StateManager stateManager;

    private final Loader loader;

    private Screen currentScreen;

    private MasterGui guiManager;

    public Game() {
        super("Game");
        loader = new Loader();
        stateManager = new StateManager(loader);
    }


    public void start() {
        super.start();
        Logger.log("start");
        running = true;
    }


    float[] vertices = new float[]{
            -0.5f, 0.5f, 0,     //S 0
            -0.5f, -0.5f, 0,    //S 1
            0.5f, -0.5f, 0,     //S 2
            0.5f, 0.5f, 0       //S 3
    };

    int[] indices = new int[]{
            0, 1, 3,            // Triangles en haut a droite S 0,1,2
            3, 1, 2             // Triangles en bas a gauche S 1,3,2
    };

    float[] textureCoords = new float[]{
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

    private void init() {
        DisplayManager.createDisplay();
        AudioManager.init(AL11.AL_EXPONENT_DISTANCE);

        guiManager = new MasterGui(loader);

        glfwShowWindow(glfwGetCurrentContext());
        stateManager.init(guiManager);
        ResourcePack pack = stateManager.getPack();
        gameFont = new FontType(pack.getTextureByName().get("candara").getTextureID(), "candara");
    }


    public void run() {
        init();
        while (running) {
            if (glfwWindowShouldClose(glfwGetCurrentContext())) {
                exit();
                return;
            }
            loop();
        }
    }

    long timer = System.currentTimeMillis();
    long beforeUpdate = System.nanoTime();
    long beforeRender = System.nanoTime();
    double elapsedUpdate;
    double elapsedRender;
    double updateTime = 1000000000.0 / 60;
    double renderTime = 1000000000.0 / 60;
    int ticks = 0;
    int frames = 0;

    private void loop() {
        long now = System.nanoTime();
        elapsedUpdate = now - beforeUpdate;
        elapsedRender = now - beforeRender;

        if (elapsedUpdate > updateTime) {
            beforeUpdate += updateTime;
            update();
            ticks++;
        } else if (elapsedRender > renderTime) {
            render();
            DisplayManager.updateDisplay();
            frames++;
            beforeRender += renderTime;
        } else {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (System.currentTimeMillis() - timer > 1000) {
            timer += 1000;
            glfwSetWindowTitle(glfwGetCurrentContext(), currentScreen.getTitle() + "  ||  " + ticks + " ticks " + frames + " fps");
            ticks = 0;
            frames = 0;
        }
    }

    private void update() {
        Input.updateInput(glfwGetCurrentContext());

        currentScreen = stateManager.getCurrentState(stateManager.update());
        currentScreen.update();
        glfwPollEvents();
    }

    private void render() {
        if (currentScreen instanceof World) {
            ((World) currentScreen).render();
        } else {
            OpenGlUtils.clearGL();
        }
        guiManager.render();

    }

    private void exit() {
        guiManager.cleanUp();
        stateManager.cleanUp();
        running = false;
        AudioManager.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        Logger.log("Exiting");
    }

}
