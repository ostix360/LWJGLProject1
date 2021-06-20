package fr.ostix.game.world;

import fr.ostix.game.audio.SoundListener;
import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.core.Input;
import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.Light;
import fr.ostix.game.entity.Player;
import fr.ostix.game.entity.camera.Camera;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.model.Texture;
import fr.ostix.game.graphics.render.MasterRenderer;
import fr.ostix.game.menu.Screen;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.world.interaction.InteractionWorld;
import fr.ostix.game.world.texture.TerrainTexture;
import fr.ostix.game.world.texture.TerrainTexturePack;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.openal.AL10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class World extends Screen {

    private MasterRenderer renderer;

    private boolean isInit = false;
    private final InteractionWorld interactionWorld = new InteractionWorld();

    public static final int MAX_LIGHTS = 2;


    private HashMap<String, Texture> textures;
    private HashMap<String, SoundSource> sounds;
    private HashMap<String, Model> models;

    private final List<Entity> entities = new ArrayList<>();
    private final List<Light> lights = new ArrayList<>();
    private static final List<Terrain> terrains = new ArrayList<>();


    private static int[][] worldIndex;

    SoundListener listener;
    Entity entity;
    Player player;
    Camera cam;

    public World() {
        super("World");
    }

    public void initWorld(Loader loader, ResourcePack pack) {
        this.textures = pack.getTextureByName();
        this.sounds = pack.getSoundByName();
        this.models = pack.getModelByName();

        player = new Player(pack.getModelByName().get("player"), new Vector3f(55, 0, 55), new Vector3f(0), 1);

        Light sun = new Light(new Vector3f(100000, 100000, -100000), Color.SUN);
        listener = new SoundListener(player.getPosition(), new Vector3f(), player.getRotation());
        cam = new Camera(player);
        entities.add(player);
        lights.add(sun);
        //  lights.add(sunc);

        initTerrain(loader);
        initEntity();


        renderer = new MasterRenderer(loader);

        interactionWorld.init(1f / 60f, entities,terrains);


        SoundSource back = pack.getSoundByName().get("ambient");

        // SoundSource back2 = AudioManager.loadSound("test1", 1, 10, 20);


        back.setGain(0.2f);
        back.setPosition(new Vector3f(0, 0, 0));
        back.setLooping(true);
        back.setProperty(AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
        back.play();
//        back2.setGain(0.2f);
//        back2.setPosition(new Vector3f(0,0,0));
//        back2.setLooping(true);
//        back2.setProperty(AL10.AL_SOURCE_RELATIVE,AL10.AL_TRUE);
        // back2.play();
        isInit = true;
    }

    private void initEntity() {

        // Model treeModel = new Model(LoadModel.loadModel("tree",".dae", loader), new TextureModel(loader.loadTexture("tree")).setTransparency(true));
//        Model grassModel = new Model(LoadModel.loadModel("grassModel", loader),
//                new TextureModel(loader.loadTexture("flower")));
//        grassModel.getModelTexture().setTransparency(true).setUseFakeLighting(true);
//        Model fernModel = new Model(LoadModel.loadModel("fern", loader),
//                new TextureModel(loader.loadTexture("fern")));
//        fernModel.getModelTexture().setTransparency(true);
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            float x = r.nextFloat() * 400;
            float z = r.nextFloat() * 400;
//            entities.add(new Entity(treeModel, new Vector3f(x, getTerrainHeight(x, z), z),
//                    new Vector3f(0, 0, 0), 1f));
//            x = r.nextFloat() * 1600;
//            z = r.nextFloat() * 1600;
//            entities.add(new Entity(grassModel, new Vector3f(x, getTerrainHeight( x, z),z),
//                    new Vector3f(0, 0, 0), 0.6f));
//            x = r.nextFloat() * 1600;
//            z = r.nextFloat() * 1600;
//            entities.add(new Entity(fernModel, new Vector3f(x, getTerrainHeight(x, z),z),
//                    new Vector3f(0, 0, 0), 5f));
        }
    }

    private void initTerrain(Loader loader) {
        TerrainTexture backgroundTexture = new TerrainTexture(textures.get("grassy2").getTextureID());
        TerrainTexture rTexture = new TerrainTexture(textures.get("mud").getTextureID());
        TerrainTexture gTexture = new TerrainTexture(textures.get("grassFlowers").getTextureID());
        TerrainTexture bTexture = new TerrainTexture(textures.get("path").getTextureID());

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(textures.get("blendMap").getTextureID());

        worldIndex = new int[2][2];
        int index = 0;
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                terrains.add(new Terrain(x, z, loader, texturePack, blendMap, "heightmap"));
                worldIndex[x][z] = index;
                index++;
            }
        }

    }

    @Override
    public void update() {
        //entity.increaseRotation(new Vector3f(0, 1, 0));
        processInteraction();
        listener.updateTransform(player.getPosition(), player.getRotation());

        if (Input.keys[GLFW.GLFW_KEY_P]){
            entity.playSound();
        }
    }

    private void processInteraction() {

        interactionWorld.update(player);
    }


    public void render() {
        cam.move();
        renderer.renderScene(entities, terrains, lights, cam);

    }

    public static float getTerrainHeight(float worldX, float worldZ) {
        int x = (int) Math.floor(worldX / Terrain.getSIZE());
        int z = (int) Math.floor(worldZ / Terrain.getSIZE());
        try {
            return terrains.get(worldIndex[x][z]).getHeightOfTerrain(worldX, worldZ);
        } catch (Exception e) {
            // Logger.err("World doesn't exist in this coordinates xIndex : " + x + " ZIndex : " + z);
        }
        return 0;

    }

    @Override
    public void cleanUp() {
        interactionWorld.finish();
        renderer.cleanUp();
    }

    public boolean isInit() {
        return isInit;
    }
}
